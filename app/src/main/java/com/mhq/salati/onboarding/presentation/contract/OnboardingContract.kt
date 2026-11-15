package com.mhq.salati.onboarding.presentation.contract

import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi

object OnboardingContract {

    enum class StepUi { WELCOME, CALCULATION, ALERTS, LOCATION }
    enum class SelectorUi { LANGUAGE, THEME, TIME_FORMAT, CALCULATION_METHOD, MADHAB, ADHAN_SOUND }

    /** What the main button of the current step does. */
    enum class PrimaryActionUi {
        CONTINUE,
        GET_STARTED,
        ALLOW_NOTIFICATIONS,
        OPEN_APP_SETTINGS,
        ALLOW_EXACT_ALARMS,
        ALLOW_LOCATION,
        ENABLE_LOCATION_SERVICES,
        CONNECT_INTERNET
    }

    data class State(
        val step: StepUi = StepUi.WELCOME,
        val isMovingForward: Boolean = true,
        // Welcome choices. Every default mirrors AppSettings, so Continue
        // always works and the user is never forced to pick anything.
        val language: AppLanguageUi = AppLanguageUi.ENGLISH,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_24_HOUR,
        // Calculation choices.
        val calculationMethod: CalculationMethodUi = CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY,
        val madhab: MadhabUi = MadhabUi.SHAFI,
        // Alerts choices.
        val notificationsEnabled: Boolean = true,
        val adhanSound: AdhanSoundUi = AdhanSoundUi.DEFAULT,
        val isNotificationPermissionGranted: Boolean = false,
        val isNotificationPermanentlyDenied: Boolean = false,
        val isExactAlarmGranted: Boolean = false,
        // Location step. Null until the first check has finished.
        val prerequisites: PrerequisitesUi? = null,
        val hasSavedPlace: Boolean = false,
        val savedPlaceName: String? = null,
        /** True while the first-launch warm-up (place + today's prayer times) runs. */
        val isPreparing: Boolean = false,
        val isLoading: Boolean = false
    ) {
        val isAlarmAccessMissing: Boolean
            get() = notificationsEnabled && (!isNotificationPermissionGranted || !isExactAlarmGranted)

        /** A chosen city, or all three requirements met. */
        val isLocationReady: Boolean
            get() = hasSavedPlace ||
                    (prerequisites?.let { it.hasInternet && it.servicesEnabled && it.permissionGranted } == true)

        val primaryAction: PrimaryActionUi
            get() = when (step) {
                StepUi.WELCOME, StepUi.CALCULATION -> PrimaryActionUi.CONTINUE
                StepUi.ALERTS -> when {
                    notificationsEnabled && !isNotificationPermissionGranted ->
                        if (isNotificationPermanentlyDenied) {
                            PrimaryActionUi.OPEN_APP_SETTINGS
                        } else {
                            PrimaryActionUi.ALLOW_NOTIFICATIONS
                        }

                    notificationsEnabled && !isExactAlarmGranted ->
                        PrimaryActionUi.ALLOW_EXACT_ALARMS

                    else -> PrimaryActionUi.CONTINUE
                }

                StepUi.LOCATION -> when {
                    isLocationReady || prerequisites == null -> PrimaryActionUi.GET_STARTED
                    !prerequisites.permissionGranted ->
                        if (prerequisites.permissionPermanentlyDenied) {
                            PrimaryActionUi.OPEN_APP_SETTINGS
                        } else {
                            PrimaryActionUi.ALLOW_LOCATION
                        }

                    !prerequisites.servicesEnabled -> PrimaryActionUi.ENABLE_LOCATION_SERVICES
                    else -> PrimaryActionUi.CONNECT_INTERNET
                }
            }

        /** The main button waits for the first location check; nothing is pressable while loading. */
        val isPrimaryEnabled: Boolean
            get() = !isLoading && (step != StepUi.LOCATION || prerequisites != null)

        /** Steps whose requirement is still unmet can be skipped. */
        val canSkip: Boolean
            get() = when (step) {
                StepUi.ALERTS -> isAlarmAccessMissing
                StepUi.LOCATION -> !isLocationReady
                else -> false
            }
    }

    sealed interface Intent {
        data class ScreenResumed(val showLocationRationale: Boolean) : Intent

        // Welcome choices — each applies live, one intent per selector.
        data class LanguageSelected(val language: AppLanguageUi) : Intent
        data class ThemeSelected(val mode: ThemeModeUi) : Intent
        data class TimeFormatSelected(val format: TimeFormatUi) : Intent

        // Calculation choices.
        data class CalculationMethodSelected(val method: CalculationMethodUi) : Intent
        data class MadhabSelected(val madhab: MadhabUi) : Intent

        // Alerts choices.
        data class NotificationsToggled(val enabled: Boolean) : Intent
        data class AdhanSoundSelected(val sound: AdhanSoundUi) : Intent
        data class NotificationPermissionResult(
            val granted: Boolean,
            val permanentlyDenied: Boolean = false
        ) : Intent

        // Location step.
        data object LocationPermissionRequested : Intent
        data object LocationPermissionGranted : Intent
        data class LocationPermissionDenied(val showRationale: Boolean) : Intent
        data class LocationServicesToggled(val enabled: Boolean) : Intent
        data class ConnectivityChanged(val isConnected: Boolean) : Intent
        data object AppSettingsRequested : Intent
        data object LocationSettingsRequested : Intent
        data object InternetSettingsRequested : Intent
        data object LocationPickerRequested : Intent

        data object PrimaryActionRequested : Intent
        data object Skipped : Intent
        data object BackRequested : Intent
    }

    sealed interface Effect {
        data object OpenAppSettings : Effect
        data object RequestNotificationPermission : Effect
        data object RequestExactAlarmPermission : Effect
        data object RequestLocationPermission : Effect
        data object OpenLocationSettings : Effect
        data object OpenInternetSettings : Effect
        data object NavigateToLocationPicker : Effect

        /** The locale to apply before the app recreates with the new language. */
        data class ApplyLanguage(val languageCode: String) : Effect

        data class ShowError(val message: MessageUi) : Effect
        data object NavigateToHome : Effect
    }
}