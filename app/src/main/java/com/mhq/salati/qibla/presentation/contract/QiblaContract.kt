package com.mhq.salati.qibla.presentation.contract

import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.location.LocationPermissionState

class QiblaContract {

    data class State(
        val isLoading: Boolean = false,
        val qiblaBearing: Float? = null,
        val deviceHeading: Float = 0f,
        val compassAccuracy: CompassAccuracy = CompassAccuracy.HIGH,
        val errorMessage: String? = null,
        val sensorUnavailable: Boolean = false,
        val locationPermission: LocationPermissionState = LocationPermissionState()
    )

    sealed interface Intent {
        data object LoadQibla : Intent
        data object Retry : Intent
        data object LocationPermissionGranted : Intent
        data class LocationPermissionDenied(val permanentlyDenied: Boolean) : Intent
        data object AccessAppSettings : Intent
        data object AccessDeviceLocationSettings : Intent
        data object LocationPillClicked : Intent
        data object RecalibrateClicked : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        data object LocationPickerNotImplemented : Effect
        data object CompassCalibrationNotImplemented : Effect
    }
}