package com.mhq.salati.home.presentation.contract

import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import java.time.Duration
import java.time.LocalDate

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val locationUiState: LocationUiState = LocationUiState(),
        val prayerTimesUiState: PrayerTimesUiState = PrayerTimesUiState(),
        val timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_12_HOUR,
        val dateBrowserUiState: DateBrowserUiState = DateBrowserUiState(),
        val adhanUiState: AdhanUiState = AdhanUiState(),
        val errorMessage: MessageUi? = null,
        val prerequisites: PrerequisitesUi? = null
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
        val locationName: String? = null
    )

    data class AdhanUiState(
        val mutedPrayers: Set<PrayerNameUi> = emptySet()
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
        data object TodayRequested : Intent
        data object EndOfMonthRequested : Intent
        data object LocationPermissionRequested : Intent
        data object LocationPermissionGranted : Intent
        data object AppSettingsRequested : Intent
        data object LocationSettingsRequested : Intent
        data object InternetSettingsRequested : Intent
        data object LocationPickerRequested : Intent
        data class ScreenResumed(val showLocationRationale: Boolean) : Intent
        data class ToggleMute(val prayerName: PrayerNameUi) : Intent
        data class LocationPermissionDenied(val showRationale: Boolean) : Intent
        data class LocationServicesToggled(val enabled: Boolean) : Intent
        data class ConnectivityChanged(val isConnected: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data object RequestLocationPermission : Effect
        data object NavigateToAppSettings : Effect
        data object NavigateToLocationSettings : Effect
        data object NavigateToInternetSettings : Effect
        data object NavigateToLocationPicker : Effect
    }
}