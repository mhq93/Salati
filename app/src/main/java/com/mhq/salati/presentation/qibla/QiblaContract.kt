package com.mhq.salati.presentation.qibla

class QiblaContract {

    data class State(
        val isLoading: Boolean = true,
        val qiblaBearing: Float? = null,
        val deviceHeading: Float = 0f,
        val errorMessage: String? = null,
        val sensorUnavailable: Boolean = false,
        val locationPermissionRequired: Boolean = false,
        val locationPermissionPermanentlyDenied: Boolean = false,
        val locationServicesDisabled: Boolean = false
    )

    sealed interface Intent {
        data object LoadQibla : Intent
        data object Retry : Intent
        data object LocationPermissionGranted : Intent
        data object AccessAppSettings : Intent
        data object AccessDeviceLocationSettings : Intent
        data class LocationPermissionDenied(val permanentlyDenied: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        data object NavigateToAppSettings : Effect
        data object NavigateToLocationSettings : Effect
    }
}