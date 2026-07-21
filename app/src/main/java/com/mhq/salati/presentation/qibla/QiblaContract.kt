package com.mhq.salati.presentation.qibla

import com.mhq.salati.domain.model.qibla.CompassAccuracy
import com.mhq.salati.presentation.common.location.LocationPermissionState

class QiblaContract {

    data class State(
        val isLoading: Boolean = true,
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
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
    }
}