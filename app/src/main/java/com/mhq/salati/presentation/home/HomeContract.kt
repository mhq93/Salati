package com.mhq.salati.presentation.home

import com.mhq.salati.domain.model.PrayerDate
import com.mhq.salati.domain.model.PrayerTimings

class HomeContract {

    data class State(
        val date: PrayerDate? = null,
        val timings: PrayerTimings? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val locationPermissionRequired: Boolean = false,
        val locationPermissionPermanentlyDenied: Boolean = false,
        val locationServicesDisabled: Boolean = false
    )

    sealed interface Intent {
        data object Retry : Intent
        data object LoadPrayerTimes : Intent
        data object AccessAppSettings : Intent
        data object AccessDeviceLocationSettings : Intent
        data object LocationPermissionGranted : Intent
        data class LocationPermissionDenied(val permanentlyDenied: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        data object NavigateToAppSettings : Effect
        data object NavigateToLocationSettings : Effect
    }
}