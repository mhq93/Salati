package com.mhq.salati.qibla.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.R
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
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
) : ViewModel() {

    private val permissionDelegate = LocationPermissionDelegate()
    val permissionEffect: SharedFlow<LocationPermissionEffect> = permissionDelegate.effect

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<QiblaContract.Effect>()
    val effect: SharedFlow<QiblaContract.Effect> = _effect.asSharedFlow()

    private var locationPermissionAutoPromptShown = false   
    private var loadQiblaJob: Job? = null

    init {
        viewModelScope.launch {
            permissionDelegate.state.collect { permissionState ->
                _state.update { it.copy(locationPermission = permissionState) }
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
                viewModelScope.launch {
                    permissionDelegate.onPermissionGranted()
                }
                loadQibla()
            }

            is QiblaContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch {
                    permissionDelegate.onPermissionDenied(intent.permanentlyDenied)
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
                viewModelScope.launch {
                    permissionDelegate.requestAppSettings()
                }
            }

            is QiblaContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch {
                    permissionDelegate.requestLocationSettings()
                }
            }

            is QiblaContract.Intent.LocationPillClicked -> {
                viewModelScope.launch { _effect.emit(QiblaContract.Effect.NavigateToLocationPicker) }
            }

            is QiblaContract.Intent.RecalibrateClicked -> {
                _state.update {
                    it.copy(isCalibrationGuideVisible = true)
                }
            }

            is QiblaContract.Intent.DismissCalibrationGuide -> {
                _state.update {
                    it.copy(isCalibrationGuideVisible = false)
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
            permissionDelegate.reset()
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    sensorUnavailable = false
                )
            }
            try {
                val savedLocation = getSavedLocationUseCase().first()
                val location = if (savedLocation != null) {
                    savedLocation
                } else {
                    if (!connectivityChecker.isConnected()) {
                        _state.update { it.copy(isLoading = false, errorMessage = UiText.Res(R.string.no_internet_connection)) }
                        return@launch
                    }

                    if (!locationProvider.isLocationEnabled()) {
                        permissionDelegate.markServicesDisabled()
                        _state.update { it.copy(isLoading = false, errorMessage = UiText.Res(R.string.location_services_disabled)) }
                        return@launch
                    }

                    if (!permissionChecker.hasLocationPermission()) {
                        if (locationPermissionAutoPromptShown) {
                            _state.update { it.copy(isLoading = false, errorMessage = UiText.Res(R.string.location_permission_required)) }
                        } else {
                            locationPermissionAutoPromptShown = true
                            permissionDelegate.requirePermission()
                            _state.update { it.copy(isLoading = false) }
                        }
                        return@launch
                    }

                    val gpsLocation = withTimeout(5_000L.milliseconds) { locationProvider.getCurrentLocation() }
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
                            _effect.emit(QiblaContract.Effect.ShowError(message))
                            return@launch
                        }
                    }
                }

                val latitude = location.latitude
                val longitude = location.longitude
                val bearing = getQiblaBearingUseCase(latitude, longitude)

                _state.update {
                    it.copy(
                        isLoading = false,
                        qiblaBearing = bearing.toFloat(),
                        locationName = location.toDisplayName()
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
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
                _effect.emit(
                    QiblaContract.Effect.ShowError(message)
                )
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
                _effect.emit(
                    QiblaContract.Effect.ShowError(message)
                )
            }
        }
    }

    private fun SavedLocation.toDisplayName(): String? = when {
        cityName != null && countryName != null -> "$cityName, $countryName"
        cityName != null -> cityName
        countryName != null -> countryName
        else -> null
    }
}