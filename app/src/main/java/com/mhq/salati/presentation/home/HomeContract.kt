package com.mhq.salati.presentation.home

import com.mhq.salati.domain.model.prayers.PrayerDate
import com.mhq.salati.domain.model.prayers.PrayerTimings
import com.mhq.salati.presentation.common.location.LocationPermissionState

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val timings: PrayerTimings? = null,
        val date: PrayerDate? = null,
        val errorMessage: String? = null,
        val locationPermission: LocationPermissionState = LocationPermissionState(),
        val mutedPrayers: Set<String> = emptySet()
    )

    sealed interface Intent {
        data object LoadPrayerTimes : Intent
        data object Retry : Intent
        data object PreviousDay : Intent
        data object NextDay : Intent
        data object LocationPermissionGranted : Intent
        data object AccessAppSettings : Intent
        data object AccessDeviceLocationSettings : Intent
        data class LocationPermissionDenied(val permanentlyDenied: Boolean) : Intent
        data class ToggleMute(val prayerName: String) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
    }
}