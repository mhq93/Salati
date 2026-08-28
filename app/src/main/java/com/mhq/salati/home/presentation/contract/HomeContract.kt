package com.mhq.salati.home.presentation.contract

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.home.domain.model.NextPrayerInfo
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.components.UiText
import java.time.LocalDate

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val prayerTimes: PrayerTimesUiState = PrayerTimesUiState(),
        val location: LocationUiState = LocationUiState(),
        val adhan: AdhanUiState = AdhanUiState(),
        val systemPermissions: SystemPermissionsUiState = SystemPermissionsUiState(),
        val dateBrowser: DateBrowserUiState = DateBrowserUiState(),
        val errorMessage: UiText? = null
    )

    data class PrayerTimesUiState(
        val date: PrayerDate? = null,
        val timings: PrayerTimings? = null,
        val currentPrayerName: PrayerName? = null,
        val nextPrayerInfo: NextPrayerInfo? = null,
        val pastPrayers: Set<PrayerName> = emptySet(),
        val remainingMillis: Long = 0L
    )

    data class LocationUiState(
        val latitude: Double? = null,
        val longitude: Double? = null,
        val locationName: String? = null,
        val isPermissionGranted: Boolean = false,
        val isPermanentlyDenied: Boolean = false,
        val areServicesDisabled: Boolean = false,
        val isPermissionRequired: Boolean = true
    )

    data class AdhanUiState(
        val playback: AdhanPlaybackState = AdhanPlaybackState.Idle,
        val mutedPrayers: Set<String> = emptySet()
    )

    data class SystemPermissionsUiState(
        val hasExactAlarm: Boolean = true,
        val hasNotification: Boolean = true
    )

    data class DateBrowserUiState(
        val currentDate: LocalDate = LocalDate.now(),
        val isBrowsingToday: Boolean = true
    )

    sealed interface Intent {
        data object LoadPrayerTimes : Intent
        data object Retry : Intent
        data object RetryClicked : Intent
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
        data object ScreenResumed : Intent
        data class LocationServicesToggled(val enabled: Boolean) : Intent
        data class ConnectivityChanged(val isConnected: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
        data object RequestExactAlarmPermission : Effect
        data object RequestNotificationPermission : Effect
        data object RequestLocationPermission : Effect
        data object NavigateToAppSettings : Effect
        data object NavigateToLocationSettings : Effect
    }
}