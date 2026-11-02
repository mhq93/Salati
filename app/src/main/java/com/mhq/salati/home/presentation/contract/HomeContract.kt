package com.mhq.salati.home.presentation.contract

import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.presentation.model.MessageUi
import java.time.Duration
import java.time.LocalDate

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val prayerTimes: PrayerTimesUiState = PrayerTimesUiState(),
        val location: LocationUiState = LocationUiState(),
        val adhan: AdhanUiState = AdhanUiState(),
        val systemPermissions: SystemPermissionsUiState = SystemPermissionsUiState(),
        val dateBrowser: DateBrowserUiState = DateBrowserUiState(),
        val errorMessage: MessageUi? = null,
        val timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_12_HOUR,
    )

    data class PrayerTimesUiState(
        val date: PrayerDateUi? = null,
        val timings: PrayerTimingsUi? = null,
        val currentPrayerName: PrayerNameUi? = null,
        val nextPrayerName: PrayerNameUi? = null,
        val pastPrayers: Set<PrayerNameUi> = emptySet(),
        val remaining: Duration = Duration.ZERO,
        val windowProgress: Float = 0f
    )

    data class LocationUiState(
        val locationName: String? = null,
        val isPermissionGranted: Boolean = false,
        val isPermanentlyDenied: Boolean = false,
        val areServicesDisabled: Boolean = false,
        val isPermissionRequired: Boolean = true
    )

    data class AdhanUiState(
        val mutedPrayers: Set<PrayerNameUi> = emptySet()
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
        data object RetryRequested : Intent
        data object PreviousDay : Intent
        data object NextDay : Intent
        data object LocationPermissionGranted : Intent
        data object AccessAppSettings : Intent
        data object AccessDeviceLocationSettings : Intent
        data class LocationPermissionDenied(val permanentlyDenied: Boolean) : Intent
        data class ToggleMute(val prayerName: PrayerNameUi) : Intent
        data object RecheckSystemPermissions : Intent
        data class NotificationPermissionResult(val granted: Boolean) : Intent
        data object ExactAlarmPermissionRequested : Intent
        data object NotificationPermissionRequested : Intent
        data object ScreenResumed : Intent
        data class LocationServicesToggled(val enabled: Boolean) : Intent
        data class ConnectivityChanged(val isConnected: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data object RequestExactAlarmPermission : Effect
        data object RequestNotificationPermission : Effect
        data object RequestLocationPermission : Effect
        data object NavigateToAppSettings : Effect
        data object NavigateToLocationSettings : Effect
    }
}