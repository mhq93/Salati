package com.mhq.salati.qibla.presentation.contract

import com.mhq.salati.permissions.location.LocationPermissionState
import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.shared.presentation.components.UiText

class QiblaContract {

    data class State(
        val isLoading: Boolean = false,
        val qiblaBearing: Float? = null,
        val deviceHeading: Float = 0f,
        val sensorUnavailable: Boolean = false,
        val compassAccuracy: CompassAccuracy = CompassAccuracy.HIGH,
        val isCalibrationGuideVisible: Boolean = false,
        val locationPermission: LocationPermissionState = LocationPermissionState(),
        val locationName: String? = null,
        val errorMessage: UiText? = null
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
        data object DismissCalibrationGuide : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
        data object LocationPickerNotImplemented : Effect
    }
}