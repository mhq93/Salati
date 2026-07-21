package com.mhq.salati.presentation.qibla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.data.sensor.CompassProvider
import com.mhq.salati.domain.usecases.qibla.GetQiblaBearingUseCase
import com.mhq.salati.presentation.common.location.LocationPermissionDelegate
import com.mhq.salati.presentation.common.location.LocationPermissionEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val compassProvider: CompassProvider,
    private val getQiblaBearingUseCase: GetQiblaBearingUseCase
) : ViewModel() {

    private val permissionDelegate = LocationPermissionDelegate()
    val permissionEffect: SharedFlow<LocationPermissionEffect> = permissionDelegate.effect

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<QiblaContract.Effect>()
    val effect: SharedFlow<QiblaContract.Effect> = _effect.asSharedFlow()

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
            is QiblaContract.Intent.LocationPermissionGranted -> loadQibla()
            is QiblaContract.Intent.LocationPermissionDenied -> {
                permissionDelegate.onPermissionDenied(intent.permanentlyDenied)
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
        permissionDelegate.requirePermission()
        _state.value = _state.value.copy(errorMessage = null)
    }

    private fun loadQibla() {
        viewModelScope.launch {
            permissionDelegate.reset()
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            if (!locationProvider.isLocationEnabled()) {
                permissionDelegate.markServicesDisabled()
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Location services are turned off. Please enable them."
                )
                return@launch
            }

            try {
                val location = locationProvider.getCurrentLocation()
                val latitude = location.latitude
                val longitude = location.longitude
                //val (latitude, longitude) = locationProvider.getCurrentLocation()
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