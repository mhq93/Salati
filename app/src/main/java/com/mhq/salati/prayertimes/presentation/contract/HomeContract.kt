package com.mhq.salati.prayertimes.presentation.contract

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.permissions.location.LocationPermissionState

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val timings: PrayerTimings? = null,
        val date: PrayerDate? = null,
        val errorMessage: String? = null,
        val locationPermission: LocationPermissionState = LocationPermissionState(),
        val adhanPlayback: AdhanPlaybackState = AdhanPlaybackState.Idle,
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
        data object StopAdhanClicked : Intent
        data class ToggleMute(val prayerName: String) : Intent
    }

    sealed interface Effect {
        data object RequestExactAlarmPermission : Effect
        data object RequestNotificationPermission : Effect
        data class ShowError(val message: String) : Effect
    }
}