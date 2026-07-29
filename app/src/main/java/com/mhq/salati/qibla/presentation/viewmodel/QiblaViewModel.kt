package com.mhq.salati.qibla.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.location.data.LocationProvider
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.FetchAndSaveLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
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
import javax.inject.Inject

@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val getQiblaBearingUseCase: GetQiblaBearingUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val fetchAndSaveLocationUseCase: FetchAndSaveLocationUseCase,
    private val permissionChecker: PermissionChecker,
    private val locationProvider: LocationProvider,
    private val compassProvider: CompassProvider,
) : ViewModel() {

    private val permissionDelegate = LocationPermissionDelegate()
    val permissionEffect: SharedFlow<LocationPermissionEffect> = permissionDelegate.effect

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<QiblaContract.Effect>()
    val effect: SharedFlow<QiblaContract.Effect> = _effect.asSharedFlow()

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
                            //"Location permission permanently denied. Please, enable it from Settings."
                            UiText.Res(R.string.location_permission_permanently_denied)

                        } else {
                            //"Location permission is required to show Qibla direction."
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
                viewModelScope.launch {
                    _effect.emit(QiblaContract.Effect.LocationPickerNotImplemented)
                }
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
        viewModelScope.launch {
            if (permissionChecker.hasLocationPermission()) {
                permissionDelegate.onPermissionGranted()
                loadQibla()
            } else {
                permissionDelegate.requirePermission()
            }
        }
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
                    if (!locationProvider.isLocationEnabled()) {
                        permissionDelegate.markServicesDisabled()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = UiText.Res(R.string.location_services_disabled)
                                //errorMessage = "Location services are turned off. Please, enable them."
                            )
                        }
                        return@launch
                    }

                    if (!permissionChecker.hasLocationPermission()) {
                        permissionDelegate.requirePermission()
                        _state.update { it.copy(isLoading = false) }
                        return@launch
                    }

                    fetchAndSaveLocationUseCase()
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
                        //errorMessage = "Failed to get location"
                    )
                }
                //_effect.emit(QiblaContract.Effect.ShowError("Failed to get location"))
                _effect.emit(QiblaContract.Effect.ShowError(message))
            } catch (e: Exception) {
                val isSensorMissing = e.message?.contains("not available") == true
                val message = e.message?.let { UiText.Raw(it) }
                    ?: UiText.Res(R.string.failed_to_load_qibla_direction)
                //val message = e.message ?: "Failed to load Qibla direction"
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message,
                        sensorUnavailable = isSensorMissing
                    )
                }
                _effect.emit(QiblaContract.Effect.ShowError(message))
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