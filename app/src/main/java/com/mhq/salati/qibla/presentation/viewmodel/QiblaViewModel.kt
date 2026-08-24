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
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

//NOMINATIM…
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
    private val observeSettings: ObserveSettingsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    // FIX: Single Channel for exactly-once effect delivery
    private val _effect = Channel<QiblaContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var locationPermissionAutoPromptShown = false
    private var loadQiblaJob: Job? = null

    init {
        // FIX: Map internal delegate effects into contract Effect stream
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

        // FIX: Flatten delegate state into UI-centric boolean flags
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

        viewModelScope.launch {
            observeSettings().collect { settings ->
                val saved = getSavedLocationUseCase().first() ?: return@collect
                val localized = getLocalizedLocationName(saved, settings.language.code)
                _state.update { it.copy(locationName = localized) }
            }
        }
    }

    fun onIntent(intent: QiblaContract.Intent) {
        when (intent) {
            is QiblaContract.Intent.LoadQibla -> checkPermissionAndLoad()
            is QiblaContract.Intent.Retry -> checkPermissionAndLoad()
            is QiblaContract.Intent.RetryClicked -> {
                locationPermissionAutoPromptShown = false
                checkPermissionAndLoad()
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
                viewModelScope.launch { _effect.send(QiblaContract.Effect.NavigateToLocationPicker) }
            }
            is QiblaContract.Intent.RecalibrateClicked -> {
                _state.update { it.copy(isCalibrationGuideVisible = true) }
            }
            is QiblaContract.Intent.DismissCalibrationGuide -> {
                _state.update { it.copy(isCalibrationGuideVisible = false) }
            }
            // FIX: Moved conditional retry logic from Container into ViewModel
            is QiblaContract.Intent.ScreenResumed -> {
                val s = _state.value
                if (s.isLocationPermissionPermanentlyDenied || s.areLocationServicesDisabled) {
                    checkPermissionAndLoad()
                }
            }
            is QiblaContract.Intent.LocationServicesToggled -> {
                if (intent.enabled && _state.value.areLocationServicesDisabled) {
                    checkPermissionAndLoad()
                }
            }
        }
    }

    private fun checkPermissionAndLoad() {
        _state.update { it.copy(errorMessage = null) }
        loadQibla()
    }

    private fun loadQibla() {
        loadQiblaJob?.cancel()
        loadQiblaJob = viewModelScope.launch {
            locationPermissionDelegate.reset()
            _state.update { it.copy(isLoading = true, errorMessage = null, sensorUnavailable = false) }

            try {
                val savedLocation = getSavedLocationUseCase().first()
                val location = savedLocation ?: resolveFreshLocation() ?: return@launch

                val latitude = location.latitude
                val longitude = location.longitude
                val bearing = getQiblaBearingUseCase(latitude, longitude)
                val lang = observeSettings().first().language.code
                val localizedName = getLocalizedLocationName(location, lang)

                _state.update {
                    it.copy(
                        isLoading = false,
                        qiblaBearing = bearing.toFloat(),
                        locationName = localizedName
                    )
                }

                compassProvider.getHeadingFlow(latitude, longitude).collect { reading ->
                    _state.update {
                        it.copy(
                            deviceHeading = reading.headingDegrees,
                            compassAccuracy = reading.accuracy
                        )
                    }
                }
            } catch (e: TimeoutCancellationException) {
                val message = UiText.Res(R.string.failed_to_get_location)
                _state.update { it.copy(isLoading = false, errorMessage = message) }
                _effect.send(QiblaContract.Effect.ShowError(message))
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
     * Returns the resolved location,
     * or null if a gate blocked/aborted the flow
     * (already updated state/effects itself).
     */
    private suspend fun resolveFreshLocation(): SavedLocation? {
        if (!connectivityChecker.isConnected()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.no_internet_connection)
                )
            }
            return null
        }
        if (!locationProvider.isLocationEnabled()) {
            locationPermissionDelegate.markServicesDisabled()
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.location_services_disabled)
                )
            }
            return null
        }
        if (!permissionChecker.hasLocationPermission()) {
            // FIX
            if (locationPermissionDelegate.state.value.permanentlyDenied) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
                    )
                }
                return null
            }

            if (locationPermissionAutoPromptShown) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_required)
                    )
                }
            } else {
                locationPermissionAutoPromptShown = true
                locationPermissionDelegate.requirePermission()
                _state.update { it.copy(isLoading = false) }
            }
            return null
        }

        val gpsLocation = withTimeout(5_000L.milliseconds) {
            locationProvider.getCurrentLocation()
        }
        val lat = gpsLocation.latitude
        val lng = gpsLocation.longitude

        return when (val geocode = reverseGeocodeLocationUseCase(lat, lng)) {
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
    }
}

//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.mhq.salati.R
//import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
//import com.mhq.salati.location.domain.GeocodeResult
//import com.mhq.salati.location.domain.model.SavedLocation
//import com.mhq.salati.location.domain.repo.LocationProvider
//import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
//import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
//import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
//import com.mhq.salati.permissions.domain.PermissionChecker
//import com.mhq.salati.permissions.location.LocationPermissionDelegate
//import com.mhq.salati.permissions.location.LocationPermissionEffect
//import com.mhq.salati.qibla.data.sensor.CompassProvider
//import com.mhq.salati.qibla.domain.usecases.GetQiblaBearingUseCase
//import com.mhq.salati.qibla.presentation.contract.QiblaContract
//import com.mhq.salati.shared.presentation.components.UiText
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CancellationException
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.TimeoutCancellationException
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withTimeout
//import javax.inject.Inject
//import kotlin.time.Duration.Companion.milliseconds
//
//@HiltViewModel
//class QiblaViewModel @Inject constructor(
//    private val compassProvider: CompassProvider,
//    private val locationProvider: LocationProvider,
//    private val permissionChecker: PermissionChecker,
//    private val connectivityChecker: ConnectivityChecker,
//    private val getQiblaBearingUseCase: GetQiblaBearingUseCase,
//    private val getSavedLocationUseCase: GetSavedLocationUseCase,
//    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
//    private val saveManualLocationUseCase: SaveManualLocationUseCase,
//    private val locationPermissionDelegate: LocationPermissionDelegate
//) : ViewModel() {
//
//    private val _state = MutableStateFlow(QiblaContract.State())
//    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()
//
//    // FIX: Single Channel for exactly-once effect delivery
//    private val _effect = Channel<QiblaContract.Effect>(Channel.BUFFERED)
//    val effect = _effect.receiveAsFlow()
//
//    private var locationPermissionAutoPromptShown = false
//    private var loadQiblaJob: Job? = null
//
//    init {
//        // FIX: Map internal delegate effects into contract Effect stream
//        viewModelScope.launch {
//            locationPermissionDelegate.effect.collect { delegateEffect ->
//                val mapped = when (delegateEffect) {
//                    is LocationPermissionEffect.RequestPermission ->
//                        QiblaContract.Effect.RequestLocationPermission
//                    is LocationPermissionEffect.NavigateToAppSettings ->
//                        QiblaContract.Effect.NavigateToAppSettings
//                    is LocationPermissionEffect.NavigateToLocationSettings ->
//                        QiblaContract.Effect.NavigateToLocationSettings
//                    is LocationPermissionEffect.PermissionResolved -> null
//                }
//                mapped?.let { _effect.send(it) }
//            }
//        }
//
//        // FIX: Flatten delegate state into UI-centric boolean flags
//        viewModelScope.launch {
//            locationPermissionDelegate.state.collect { ps ->
//                _state.update {
//                    it.copy(
//                        isLocationPermissionGranted = ps.granted,
//                        isLocationPermissionPermanentlyDenied = ps.permanentlyDenied,
//                        areLocationServicesDisabled = ps.servicesDisabled,
//                        isLocationPermissionRequired = ps.required
//                    )
//                }
//            }
//        }
//    }
//
//    fun onIntent(intent: QiblaContract.Intent) {
//        when (intent) {
//            is QiblaContract.Intent.LoadQibla -> checkPermissionAndLoad()
//            is QiblaContract.Intent.Retry -> checkPermissionAndLoad()
//            is QiblaContract.Intent.RetryClicked -> {
//                locationPermissionAutoPromptShown = false
//                checkPermissionAndLoad()
//            }
//            is QiblaContract.Intent.LocationPermissionGranted -> {
//                viewModelScope.launch { locationPermissionDelegate.onPermissionGranted() }
//                loadQibla()
//            }
//            is QiblaContract.Intent.LocationPermissionDenied -> {
//                viewModelScope.launch {
//                    locationPermissionDelegate.onPermissionDenied(intent.permanentlyDenied)
//                }
//                _state.update {
//                    it.copy(
//                        errorMessage = if (intent.permanentlyDenied) {
//                            UiText.Res(R.string.location_permission_permanently_denied)
//                        } else {
//                            UiText.Res(R.string.location_permission_required)
//                        }
//                    )
//                }
//            }
//            is QiblaContract.Intent.AccessAppSettings -> {
//                viewModelScope.launch { locationPermissionDelegate.requestAppSettings() }
//            }
//            is QiblaContract.Intent.AccessDeviceLocationSettings -> {
//                viewModelScope.launch { locationPermissionDelegate.requestLocationSettings() }
//            }
//            is QiblaContract.Intent.LocationPillClicked -> {
//                viewModelScope.launch { _effect.send(QiblaContract.Effect.NavigateToLocationPicker) }
//            }
//            is QiblaContract.Intent.RecalibrateClicked -> {
//                _state.update { it.copy(isCalibrationGuideVisible = true) }
//            }
//            is QiblaContract.Intent.DismissCalibrationGuide -> {
//                _state.update { it.copy(isCalibrationGuideVisible = false) }
//            }
//            // FIX: Moved conditional retry logic from Container into ViewModel
//            is QiblaContract.Intent.ScreenResumed -> {
//                val s = _state.value
//                if (s.isLocationPermissionPermanentlyDenied || s.areLocationServicesDisabled) {
//                    checkPermissionAndLoad()
//                }
//            }
//            is QiblaContract.Intent.LocationServicesToggled -> {
//                if (intent.enabled && _state.value.areLocationServicesDisabled) {
//                    checkPermissionAndLoad()
//                }
//            }
//        }
//    }
//
//    private fun checkPermissionAndLoad() {
//        _state.update { it.copy(errorMessage = null) }
//        loadQibla()
//    }
//
//    private fun loadQibla() {
//        loadQiblaJob?.cancel()
//        loadQiblaJob = viewModelScope.launch {
//            locationPermissionDelegate.reset()
//            _state.update { it.copy(isLoading = true, errorMessage = null, sensorUnavailable = false) }
//
//            try {
//                val savedLocation = getSavedLocationUseCase().first()
//                val location = savedLocation ?: resolveFreshLocation() ?: return@launch
//
//                val latitude = location.latitude
//                val longitude = location.longitude
//                val bearing = getQiblaBearingUseCase(latitude, longitude)
//
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        qiblaBearing = bearing.toFloat(),
//                        locationName = location.toDisplayName()
//                    )
//                }
//
//                compassProvider.getHeadingFlow(latitude, longitude).collect { reading ->
//                    _state.update {
//                        it.copy(
//                            deviceHeading = reading.headingDegrees,
//                            compassAccuracy = reading.accuracy
//                        )
//                    }
//                }
//            } catch (e: TimeoutCancellationException) {
//                val message = UiText.Res(R.string.failed_to_get_location)
//                _state.update { it.copy(isLoading = false, errorMessage = message) }
//                _effect.send(QiblaContract.Effect.ShowError(message))
//            } catch (e: CancellationException) {
//                throw e
//            } catch (e: Exception) {
//                val isSensorMissing = e.message?.contains("not available") == true
//                val message = e.message?.let { UiText.Raw(it) }
//                    ?: UiText.Res(R.string.failed_to_load_qibla_direction)
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = message,
//                        sensorUnavailable = isSensorMissing
//                    )
//                }
//                _effect.send(QiblaContract.Effect.ShowError(message))
//            }
//        }
//    }
//
//    /**
//     * Returns the resolved location,
//     * or null if a gate blocked/aborted the flow
//     * (already updated state/effects itself).
//     */
//    private suspend fun resolveFreshLocation(): SavedLocation? {
//        if (!connectivityChecker.isConnected()) {
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = UiText.Res(R.string.no_internet_connection)
//                )
//            }
//            return null
//        }
//        if (!locationProvider.isLocationEnabled()) {
//            locationPermissionDelegate.markServicesDisabled()
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = UiText.Res(R.string.location_services_disabled)
//                )
//            }
//            return null
//        }
//        if (!permissionChecker.hasLocationPermission()) {
//            // FIX
//            if (locationPermissionDelegate.state.value.permanentlyDenied) {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
//                    )
//                }
//                return null
//            }
//
//            if (locationPermissionAutoPromptShown) {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = UiText.Res(R.string.location_permission_required)
//                    )
//                }
//            } else {
//                locationPermissionAutoPromptShown = true
//                locationPermissionDelegate.requirePermission()
//                _state.update { it.copy(isLoading = false) }
//            }
//            return null
//        }
//
//        val gpsLocation = withTimeout(5_000L.milliseconds) {
//            locationProvider.getCurrentLocation()
//        }
//        val lat = gpsLocation.latitude
//        val lng = gpsLocation.longitude
//
//        return when (val geocode = reverseGeocodeLocationUseCase(lat, lng)) {
//            is GeocodeResult.Found -> {
//                saveManualLocationUseCase(lat, lng, geocode.cityName, geocode.countryName)
//                SavedLocation(geocode.cityName, geocode.countryName, lat, lng)
//            }
//            is GeocodeResult.NotFound -> {
//                saveManualLocationUseCase(lat, lng, null, null)
//                SavedLocation(null, null, lat, lng)
//            }
//            is GeocodeResult.Failed -> {
//                val message = UiText.Res(R.string.failed_to_get_location_name)
//                _state.update { it.copy(isLoading = false, errorMessage = message) }
//                _effect.send(QiblaContract.Effect.ShowError(message))
//                null
//            }
//        }
//    }
//
//    private fun SavedLocation.toDisplayName(): String? = when {
//        cityName != null && countryName != null -> "$cityName, $countryName"
//        cityName != null -> cityName
//        countryName != null -> countryName
//        else -> null
//    }
//}