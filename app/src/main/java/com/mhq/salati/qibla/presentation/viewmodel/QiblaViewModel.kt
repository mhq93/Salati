package com.mhq.salati.qibla.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.location.domain.usecases.GetLocalizedLocationNameUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.permissions.location.LocationPermissionDelegate
import com.mhq.salati.permissions.location.LocationPermissionEffect
import com.mhq.salati.qibla.data.sensor.CompassProvider
import com.mhq.salati.qibla.domain.usecases.GetQiblaBearingUseCase
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.presentation.components.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val LOCATION_NAME_LOOKUP_TIMEOUT = 500L.milliseconds

@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val compassProvider: CompassProvider,
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker,
    private val connectivityChecker: ConnectivityChecker,
    private val getQiblaBearingUseCase: GetQiblaBearingUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
    private val saveManualLocationUseCase: SaveManualLocationUseCase,
    private val locationPermissionDelegate: LocationPermissionDelegate,
    private val getLocalizedLocationName: GetLocalizedLocationNameUseCase,
    private val observeSettings: ObserveSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = Channel<QiblaContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var locationPermissionAutoPromptShown = false
    private var loadQiblaJob: Job? = null

    init {
        viewModelScope.launch {
            locationPermissionDelegate.effect.collect { delegateEffect ->
                val mapped = when (delegateEffect) {
                    is LocationPermissionEffect.RequestPermission ->
                        QiblaContract.Effect.RequestLocationPermission
                    is LocationPermissionEffect.NavigateToAppSettings ->
                        QiblaContract.Effect.NavigateToAppSettings
                    is LocationPermissionEffect.NavigateToLocationSettings ->
                        QiblaContract.Effect.NavigateToLocationSettings
                    is LocationPermissionEffect.PermissionResolved -> null
                }
                mapped?.let { _effect.send(it) }
            }
        }

        viewModelScope.launch {
            locationPermissionDelegate.state.collect { ps ->
                _state.update {
                    it.copy(
                        isLocationPermissionGranted = ps.granted,
                        isLocationPermissionPermanentlyDenied = ps.permanentlyDenied,
                        areLocationServicesDisabled = ps.servicesDisabled,
                        isLocationPermissionRequired = ps.required
                    )
                }
            }
        }
    }

    fun onIntent(intent: QiblaContract.Intent) {
        when (intent) {
            is QiblaContract.Intent.LoadQibla -> loadQibla()
            is QiblaContract.Intent.Retry -> loadQibla()
            is QiblaContract.Intent.RetryClicked -> {
                locationPermissionAutoPromptShown = false
                loadQibla()
            }
            is QiblaContract.Intent.LocationPermissionGranted -> {
                viewModelScope.launch { locationPermissionDelegate.onPermissionGranted() }
                loadQibla()
            }
            is QiblaContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch {
                    locationPermissionDelegate.onPermissionDenied(intent.permanentlyDenied)
                }
                _state.update {
                    it.copy(
                        errorMessage = if (intent.permanentlyDenied) {
                            UiText.Res(R.string.location_permission_permanently_denied)
                        } else {
                            UiText.Res(R.string.location_permission_required)
                        }
                    )
                }
            }
            is QiblaContract.Intent.AccessAppSettings -> {
                viewModelScope.launch { locationPermissionDelegate.requestAppSettings() }
            }
            is QiblaContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch { locationPermissionDelegate.requestLocationSettings() }
            }
            is QiblaContract.Intent.LocationPillClicked -> {
                viewModelScope.launch {
                    _effect.send(QiblaContract.Effect.NavigateToLocationPicker)
                }
            }
            is QiblaContract.Intent.RecalibrateClicked -> {
                _state.update { it.copy(isCalibrationGuideVisible = true) }
            }
            is QiblaContract.Intent.DismissCalibrationGuide -> {
                _state.update { it.copy(isCalibrationGuideVisible = false) }
            }
            is QiblaContract.Intent.ScreenResumed -> {
                loadQibla()
            }
            is QiblaContract.Intent.LocationServicesToggled -> {
                if (intent.enabled && _state.value.areLocationServicesDisabled) {
                    loadQibla()
                }
            }
        }
    }

    private fun loadQibla() {
        loadQiblaJob?.cancel()

        loadQiblaJob = viewModelScope.launch {
            try {
                locationPermissionDelegate.reset()

                val savedLocation = getSavedLocationUseCase().first()

                if (savedLocation != null) {
                    handleSavedLocation(savedLocation)
                    return@launch
                }

                val freshLocation = resolveFreshLocation()
                if (freshLocation == null) return@launch

                handleFreshLocation(freshLocation)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val isSensorMissing = e.message?.contains("not available") == true
                val message = e.message?.let { UiText.Raw(it) }
                    ?: UiText.Res(R.string.failed_to_load_qibla_direction)
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message,
                        sensorUnavailable = isSensorMissing
                    )
                }
                _effect.send(QiblaContract.Effect.ShowError(message))
            }
        }
    }

    private suspend fun handleSavedLocation(savedLocation: SavedLocation) {
        val lang = observeSettings().first().language.code
        val isOnline = connectivityChecker.isConnected()

        val finalName = if (lang == "ar") {
            savedLocation.toDisplayName()
        } else if (isOnline) {
            withTimeoutOrNull(LOCATION_NAME_LOOKUP_TIMEOUT) {
                getLocalizedLocationName(savedLocation, lang)
            } ?: savedLocation.toDisplayName()
        } else {
            savedLocation.toDisplayName()
        }

        val bearing = getQiblaBearingUseCase(
            savedLocation.latitude,
            savedLocation.longitude
        )
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                qiblaBearing = bearing.toFloat(),
                locationName = finalName,
                sensorUnavailable = false,
                isLocationPermissionRequired = false,
                areLocationServicesDisabled = false,
                isLocationPermissionPermanentlyDenied = false
            )
        }

        startCompassUpdates(savedLocation.latitude, savedLocation.longitude)
    }

    private suspend fun handleFreshLocation(freshLocation: SavedLocation) {
        val lang = observeSettings().first().language.code
        val isOnline = connectivityChecker.isConnected()

        // Localize the location name
        val localizedName = if (lang != "ar" && isOnline) {
            withTimeoutOrNull(LOCATION_NAME_LOOKUP_TIMEOUT) {
                getLocalizedLocationName(freshLocation, lang)
            }
        } else {
            null
        }

        // Use localized name if available, otherwise fall back to saved location name
        val finalName = localizedName ?: freshLocation.toDisplayName()

        // If we successfully localized and have a different name, save it
        if (localizedName != null && localizedName != freshLocation.toDisplayName()) {
            val (city, country) = parseDisplayName(localizedName)
            saveManualLocationUseCase(
                freshLocation.latitude,
                freshLocation.longitude,
                city,
                country
            )
        }

        val bearing = getQiblaBearingUseCase(
            freshLocation.latitude,
            freshLocation.longitude
        )
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                qiblaBearing = bearing.toFloat(),
                locationName = finalName,
                sensorUnavailable = false,
                isLocationPermissionRequired = false,
                areLocationServicesDisabled = false,
                isLocationPermissionPermanentlyDenied = false
            )
        }

        startCompassUpdates(freshLocation.latitude, freshLocation.longitude)
    }

    private fun startCompassUpdates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            compassProvider.getHeadingFlow(latitude, longitude).collect { reading ->
                _state.update {
                    it.copy(
                        deviceHeading = reading.headingDegrees,
                        compassAccuracy = reading.accuracy
                    )
                }
            }
        }
    }

    private suspend fun resolveFreshLocation(): SavedLocation? {
        if (!connectivityChecker.isConnected()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.no_internet_connection),
                    areLocationServicesDisabled = false,
                    isLocationPermissionRequired = false,
                    isLocationPermissionPermanentlyDenied = false,
                    sensorUnavailable = false
                )
            }
            return null
        }

        if (!locationProvider.isLocationEnabled()) {
            locationPermissionDelegate.markServicesDisabled()
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.location_services_disabled),
                    areLocationServicesDisabled = true,
                    isLocationPermissionRequired = false,
                    isLocationPermissionPermanentlyDenied = false,
                    sensorUnavailable = false
                )
            }
            return null
        }

        if (!permissionChecker.hasLocationPermission()) {
            if (locationPermissionDelegate.state.value.permanentlyDenied) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_permanently_denied),
                        isLocationPermissionPermanentlyDenied = true,
                        areLocationServicesDisabled = false,
                        isLocationPermissionRequired = false,
                        sensorUnavailable = false
                    )
                }
                return null
            }

            if (locationPermissionAutoPromptShown) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_required),
                        isLocationPermissionRequired = true,
                        areLocationServicesDisabled = false,
                        isLocationPermissionPermanentlyDenied = false,
                        sensorUnavailable = false
                    )
                }
            } else {
                locationPermissionAutoPromptShown = true
                locationPermissionDelegate.requirePermission()
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        isLocationPermissionRequired = true,
                        areLocationServicesDisabled = false,
                        isLocationPermissionPermanentlyDenied = false,
                        sensorUnavailable = false
                    )
                }
            }
            return null
        }

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                sensorUnavailable = false,
                isLocationPermissionRequired = false,
                areLocationServicesDisabled = false,
                isLocationPermissionPermanentlyDenied = false
            )
        }

        return try {
            val gpsLocation = withTimeout(5_000L.milliseconds) {
                locationProvider.getCurrentLocation()
            }
            val lat = gpsLocation.latitude
            val lng = gpsLocation.longitude

            when (val geocode = reverseGeocodeLocationUseCase(lat, lng)) {
                is GeocodeResult.Found -> {
                    SavedLocation(geocode.cityName, geocode.countryName, lat, lng)
                }
                is GeocodeResult.NotFound -> {
                    SavedLocation(null, null, lat, lng)
                }
                is GeocodeResult.Failed -> {
                    val message = UiText.Res(R.string.failed_to_get_location_name)
                    _state.update { it.copy(isLoading = false, errorMessage = message) }
                    _effect.send(QiblaContract.Effect.ShowError(message))
                    null
                }
            }
        } catch (e: TimeoutCancellationException) {
            val message = UiText.Res(R.string.failed_to_get_location)
            _state.update { it.copy(isLoading = false, errorMessage = message) }
            _effect.send(QiblaContract.Effect.ShowError(message))
            null
        } catch (e: SecurityException) {
            locationPermissionDelegate.requirePermission()
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.location_permission_required),
                    isLocationPermissionRequired = true
                )
            }
            null
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.failed_to_load_qibla_direction)
            _state.update { it.copy(isLoading = false, errorMessage = message) }
            _effect.send(QiblaContract.Effect.ShowError(message))
            null
        }
    }

    private fun parseDisplayName(displayName: String?): Pair<String?, String?> {
        if (displayName == null) return null to null
        val parts = displayName.split(", ", limit = 2)
        return when (parts.size) {
            2 -> parts[0] to parts[1]
            1 -> parts[0] to null
            else -> null to null
        }
    }

    private fun SavedLocation.toDisplayName(): String? = when {
        cityName != null && countryName != null -> "$cityName, $countryName"
        cityName != null -> cityName
        countryName != null -> countryName
        else -> null
    }
}