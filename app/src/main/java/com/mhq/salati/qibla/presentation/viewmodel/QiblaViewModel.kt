package com.mhq.salati.qibla.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.permissions.location.LocationPermissionDelegate
import com.mhq.salati.permissions.location.LocationPermissionEffect
import com.mhq.salati.qibla.data.sensor.CompassProvider
import com.mhq.salati.qibla.domain.usecases.GetQiblaBearingUseCase
import com.mhq.salati.qibla.presentation.contract.QiblaContract
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
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

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
    private val locationPermissionDelegate: LocationPermissionDelegate
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

    /**
     * Loads qibla data with zero loading flash for gate errors.
     *
     * The key insight: [isLoading] = true is only set when we are about to do
     * genuine async work (fetching GPS). All gate checks (connectivity,
     * location services, permissions) run without ever touching [isLoading],
     * so there is no spinner flash when they fail.
     *
     * Order of operations for fresh location:
     *   1. Check saved location (fast local read)
     *   2. If no saved location, check connectivity FIRST
     *   3. Then check location services
     *   4. Then check permissions
     *   5. Only then set [isLoading] = true and fetch GPS
     */
    private fun loadQibla() {
        loadQiblaJob?.cancel()

        loadQiblaJob = viewModelScope.launch {
            try {
                locationPermissionDelegate.reset()

                val savedLocation = getSavedLocationUseCase().first()

                if (savedLocation != null) {
                    // Fast path: cached location needs no network, no GPS, no loading spinner
                    val bearing = getQiblaBearingUseCase(
                        savedLocation.latitude,
                        savedLocation.longitude
                    )
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            qiblaBearing = bearing.toFloat(),
                            locationName = savedLocation.toDisplayName(),
                            sensorUnavailable = false,
                            isLocationPermissionRequired = false,
                            areLocationServicesDisabled = false,
                            isLocationPermissionPermanentlyDenied = false
                        )
                    }

                    compassProvider.getHeadingFlow(
                        savedLocation.latitude,
                        savedLocation.longitude
                    ).collect { reading ->
                        _state.update {
                            it.copy(
                                deviceHeading = reading.headingDegrees,
                                compassAccuracy = reading.accuracy
                            )
                        }
                    }
                    return@launch
                }

                // No saved location — need fresh location. Check gates in order.
                val freshLocation = resolveFreshLocation()
                if (freshLocation == null) return@launch

                val bearing = getQiblaBearingUseCase(
                    freshLocation.latitude,
                    freshLocation.longitude
                )
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        qiblaBearing = bearing.toFloat(),
                        locationName = freshLocation.toDisplayName(),
                        sensorUnavailable = false,
                        isLocationPermissionRequired = false,
                        areLocationServicesDisabled = false,
                        isLocationPermissionPermanentlyDenied = false
                    )
                }

                compassProvider.getHeadingFlow(
                    freshLocation.latitude,
                    freshLocation.longitude
                ).collect { reading ->
                    _state.update {
                        it.copy(
                            deviceHeading = reading.headingDegrees,
                            compassAccuracy = reading.accuracy
                        )
                    }
                }
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

    /**
     * Attempts to resolve a fresh location from GPS + geocoding.
     *
     * Gate order (matches original logic):
     *   1. Connectivity — if offline, show "No Internet Connection"
     *   2. Location services — if disabled, show "Location services disabled"
     *   3. Permissions — if denied, show appropriate permission error or auto-prompt
     *   4. Fetch GPS + reverse geocode
     *
     * Returns null if any gate blocks the flow. State is already updated.
     */
    private suspend fun resolveFreshLocation(): SavedLocation? {
        // Gate 1: Connectivity (checked FIRST — this was the bug in the rewrite)
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

        // Gate 2: Location services
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

        // Gate 3: Permissions
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

        // All gates passed — genuine async work starts here
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
                    saveManualLocationUseCase(lat, lng, geocode.cityName, geocode.countryName)
                    SavedLocation(geocode.cityName, geocode.countryName, lat, lng)
                }
                is GeocodeResult.NotFound -> {
                    saveManualLocationUseCase(lat, lng, null, null)
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

    private fun SavedLocation.toDisplayName(): String? = when {
        cityName != null && countryName != null -> "$cityName, $countryName"
        cityName != null -> cityName
        countryName != null -> countryName
        else -> null
    }
}