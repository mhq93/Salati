package com.mhq.salati.presentation.qibla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.data.sensor.CompassProvider
import com.mhq.salati.domain.permissions.PermissionChecker
import com.mhq.salati.domain.usecases.location.FetchAndSaveLocationUseCase
import com.mhq.salati.domain.usecases.location.GetSavedLocationUseCase
import com.mhq.salati.domain.usecases.qibla.GetQiblaBearingUseCase
import com.mhq.salati.presentation.common.location.LocationPermissionDelegate
import com.mhq.salati.presentation.common.location.LocationPermissionEffect
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

    private var loadJob: Job? = null

    init {
        viewModelScope.launch {
            permissionDelegate.state.collect { permissionState ->
                _state.value = _state.value.copy(locationPermission = permissionState)
            }
        }
    }

    fun onIntent(intent: QiblaContract.Intent) {
        when (intent) {
            is QiblaContract.Intent.LoadQibla -> checkPermissionAndLoad()
            is QiblaContract.Intent.Retry -> checkPermissionAndLoad()
            is QiblaContract.Intent.LocationPermissionGranted -> {
                viewModelScope.launch { permissionDelegate.onPermissionGranted() }
                loadQibla()
            }

            is QiblaContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch { permissionDelegate.onPermissionDenied(intent.permanentlyDenied) }
                _state.value = _state.value.copy(
                    errorMessage = if (intent.permanentlyDenied) {
                        "Location permission permanently denied. Please enable it in Settings."
                    } else {
                        "Location permission is required to show Qibla direction."
                    }
                )
            }

            is QiblaContract.Intent.AccessAppSettings -> {
                viewModelScope.launch { permissionDelegate.requestAppSettings() }
            }

            is QiblaContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch { permissionDelegate.requestLocationSettings() }
            }
        }
    }

    private fun checkPermissionAndLoad() {
        _state.value = _state.value.copy(errorMessage = null)
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
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            permissionDelegate.reset()
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null,
                sensorUnavailable = false
            )

            try {
                val savedLocation = getSavedLocationUseCase().first()

                val location = if (savedLocation != null) {
                    savedLocation
                } else {
                    if (!locationProvider.isLocationEnabled()) {
                        permissionDelegate.markServicesDisabled()
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = "Location services are turned off. Please, enable them."
                        )
                        return@launch
                    }

                    if (!permissionChecker.hasLocationPermission()) {
                        permissionDelegate.requirePermission()
                        _state.value = _state.value.copy(isLoading = false)
                        return@launch
                    }

                    fetchAndSaveLocationUseCase()
                }

                val latitude = location.latitude
                val longitude = location.longitude
                val bearing = getQiblaBearingUseCase(latitude, longitude)

                _state.value = _state.value.copy(
                    isLoading = false,
                    qiblaBearing = bearing.toFloat()
                )

                compassProvider.getHeadingFlow().collect { reading ->
                    _state.value = _state.value.copy(
                        deviceHeading = reading.headingDegrees,
                        compassAccuracy = reading.accuracy
                    )
                }
            } catch (e: TimeoutCancellationException) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to get location"
                )
                _effect.emit(QiblaContract.Effect.ShowError("Failed to get location"))
            } catch (e: Exception) {
                val isSensorMissing = e.message?.contains("not available") == true
                val message = e.message ?: "Failed to load Qibla direction"
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = message,
                    sensorUnavailable = isSensorMissing
                )
                _effect.emit(QiblaContract.Effect.ShowError(message))
            }
        }
    }
}