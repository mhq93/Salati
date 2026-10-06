package com.mhq.salati.qibla.presentation.contract

import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.shared.ui.UiText

class QiblaContract {

    data class State(
        val isLoading: Boolean = false,
        val qiblaBearing: Float? = null,
        val deviceHeading: Float = 0f,
        val sensorUnavailable: Boolean = false,
        val compassAccuracy: CompassAccuracy = CompassAccuracy.HIGH,
        val isCalibrationGuideVisible: Boolean = false,

        // FIX: Flattened LocationPermissionState into UI flags
        val areLocationServicesDisabled: Boolean = false,
        val isLocationPermissionRequired: Boolean = true,
        val isLocationPermissionGranted: Boolean = false,
        val isLocationPermissionPermanentlyDenied: Boolean = false,
        val locationName: String? = null,
        val errorMessage: UiText? = null
    )

    sealed interface Intent {
        data object LoadQibla : Intent
        data object Retry : Intent
        data object RetryClicked : Intent
        data object LocationPermissionGranted : Intent
        data class LocationPermissionDenied(val permanentlyDenied: Boolean) : Intent
        data object AccessAppSettings : Intent
        data object AccessDeviceLocationSettings : Intent
        data object LocationPillClicked : Intent
        data object RecalibrateClicked : Intent
        data object DismissCalibrationGuide : Intent

        // NEW: Lifecycle events reported by UI, logic moved from Container
        data object ScreenResumed : Intent
        data object ScreenPaused : Intent
        data object CompassResumed : Intent
        data class LocationServicesToggled(val enabled: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
        data object RequestLocationPermission : Effect
        data object NavigateToLocationSettings : Effect
        data object NavigateToLocationPicker : Effect
        data object NavigateToAppSettings : Effect
    }
}