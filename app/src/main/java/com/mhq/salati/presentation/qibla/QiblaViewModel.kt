package com.mhq.salati.presentation.qibla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.data.sensor.CompassProvider
import com.mhq.salati.domain.usecases.GetQiblaBearingUseCase
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

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<QiblaContract.Effect>()
    val effect: SharedFlow<QiblaContract.Effect> = _effect.asSharedFlow()

    fun onIntent(intent: QiblaContract.Intent) {
        when (intent) {
            is QiblaContract.Intent.LoadQibla -> checkPermissionAndLoad()
            is QiblaContract.Intent.Retry -> checkPermissionAndLoad()
            is QiblaContract.Intent.LocationPermissionGranted -> loadQibla()
            is QiblaContract.Intent.LocationPermissionDenied -> {
                _state.value = _state.value.copy(
                    locationPermissionRequired = false,
                    locationPermissionPermanentlyDenied = intent.permanentlyDenied,
                    errorMessage = if (intent.permanentlyDenied) {
                        "Location permission permanently denied. Please enable it in Settings."
                    } else {
                        "Location permission is required to show Qibla direction."
                    }
                )
            }
            is QiblaContract.Intent.AccessAppSettings -> {
                viewModelScope.launch {
                    _effect.emit(QiblaContract.Effect.NavigateToAppSettings)
                }
            }
            is QiblaContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch {
                    _effect.emit(QiblaContract.Effect.NavigateToLocationSettings)
                }
            }
        }
    }

    private fun checkPermissionAndLoad() {
        _state.value = _state.value.copy(
            locationPermissionRequired = true,
            locationPermissionPermanentlyDenied = false,
            locationServicesDisabled = false,
            errorMessage = null
        )
    }

    private fun loadQibla() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null,
                locationPermissionRequired = false,
                locationServicesDisabled = false,
                locationPermissionPermanentlyDenied = false
            )

            if (!locationProvider.isLocationEnabled()) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    locationServicesDisabled = true,
                    errorMessage = "Location services are turned off. Please, enable them."
                )
                return@launch
            }

            try {
                val (latitude, longitude) = locationProvider.getCurrentLocation()
                val bearing = getQiblaBearingUseCase(
                    latitude,
                    longitude
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    qiblaBearing = bearing.toFloat()
                )

                compassProvider.getHeadingFlow().collect { heading ->
                    _state.value = _state.value.copy(deviceHeading = heading)
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