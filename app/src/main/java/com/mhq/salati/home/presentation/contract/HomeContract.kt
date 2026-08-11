package com.mhq.salati.home.presentation.contract

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.home.domain.model.NextPrayerInfo
import com.mhq.salati.permissions.location.LocationPermissionState
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.shared.presentation.components.UiText
import java.util.Calendar

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val timings: PrayerTimings? = null,
        val date: PrayerDate? = null,
        val currentPrayerName: String? = null,
        val nextPrayerInfo: NextPrayerInfo? = null,
        val latitude: Double? = null,
        val longitude: Double? = null,
        val locationName: String? = null,
        val remainingMillis: Long = 0L,
        val locationPermission: LocationPermissionState = LocationPermissionState(),
        val adhanPlayback: AdhanPlaybackState = AdhanPlaybackState.Idle,
        val mutedPrayers: Set<String> = emptySet(),
        val pastPrayers: Set<String> = emptySet(),
        val errorMessage: UiText? = null,
        val hasExactAlarmPermission: Boolean = true,
        val hasNotificationPermission: Boolean = true,
        val currentDate: Calendar = Calendar.getInstance()
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
        data object RecheckSystemPermissions : Intent
        data class NotificationPermissionResult(val granted: Boolean) : Intent
        data object ExactAlarmBannerClicked : Intent
        data object NotificationBannerClicked : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
        data object RequestExactAlarmPermission : Effect
        data object RequestNotificationPermission : Effect
    }
}