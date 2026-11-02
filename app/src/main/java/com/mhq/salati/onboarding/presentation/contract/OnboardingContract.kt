package com.mhq.salati.onboarding.presentation.contract

import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi

object OnboardingContract {

    enum class StepUi { WELCOME, CONNECT, CALCULATION, ALERTS }
    enum class SelectorUi { LANGUAGE, THEME, TIME_FORMAT, CALCULATION_METHOD, MADHAB, ADHAN_SOUND }

    /** What the main button of the current step does. */
    enum class PrimaryActionUi {
        CONTINUE,
        GET_STARTED,
        ALLOW_NOTIFICATIONS,
        OPEN_APP_SETTINGS,
        ALLOW_EXACT_ALARMS
    }

    data class State(
        val step: StepUi = StepUi.WELCOME,
        val isMovingForward: Boolean = true,
        // Welcome choices. Every default mirrors AppSettings, so Continue
        // always works and the user is never forced to pick anything.
        val language: AppLanguageUi = AppLanguageUi.ENGLISH,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_24_HOUR,
        // Connect statuses.
        val isInternetConnected: Boolean = false,
        val areLocationServicesEnabled: Boolean = false,
        val isLocationPermissionGranted: Boolean = false,
        val isLocationPermanentlyDenied: Boolean = false,
        // Calculation choices.
        val calculationMethod: CalculationMethodUi = CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY,
        val madhab: MadhabUi = MadhabUi.SHAFI,
        // Alerts choices.
        val notificationsEnabled: Boolean = true,
        val adhanSound: AdhanSoundUi = AdhanSoundUi.DEFAULT,
        val isNotificationPermissionGranted: Boolean = false,
        val isNotificationPermanentlyDenied: Boolean = false,
        val isExactAlarmGranted: Boolean = false,
        val isLoading: Boolean = false
    ) {
        val isLocationReady: Boolean
            get() = isLocationPermissionGranted && areLocationServicesEnabled

        val isAlarmAccessMissing: Boolean
            get() = notificationsEnabled && (!isNotificationPermissionGranted || !isExactAlarmGranted)

        val primaryAction: PrimaryActionUi
            get() = when (step) {
                // Connect rows each carry their own action button, so the main
                // button on WELCOME / CONNECT / CALCULATION is always Continue.
                StepUi.WELCOME, StepUi.CONNECT, StepUi.CALCULATION -> PrimaryActionUi.CONTINUE
                StepUi.ALERTS -> when {
                    notificationsEnabled && !isNotificationPermissionGranted ->
                        if (isNotificationPermanentlyDenied) {
                            PrimaryActionUi.OPEN_APP_SETTINGS
                        } else {
                            PrimaryActionUi.ALLOW_NOTIFICATIONS
                        }

                    notificationsEnabled && !isExactAlarmGranted ->
                        PrimaryActionUi.ALLOW_EXACT_ALARMS

                    else -> PrimaryActionUi.GET_STARTED
                }
            }

        /** Steps whose requirement is still unmet can be skipped. */
        val canSkip: Boolean
            get() = when (step) {
                StepUi.CONNECT -> !isInternetConnected || !isLocationReady
                StepUi.ALERTS -> isAlarmAccessMissing
                else -> false
            }
    }

    sealed interface Intent {
        data object ScreenResumed : Intent
        data class InternetStatusChanged(val connected: Boolean) : Intent
        data class LocationServicesStatusChanged(val enabled: Boolean) : Intent
        data class LocationPermissionResult(
            val granted: Boolean,
            val permanentlyDenied: Boolean
        ) : Intent

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

        // The Connect rows each own their action button.
        data object InternetActionRequested : Intent
        data object LocationActionRequested : Intent

        data object PrimaryActionRequested : Intent
        data object Skipped : Intent
        data object BackRequested : Intent
    }

    sealed interface Effect {
        data object OpenInternetSettings : Effect
        data object OpenLocationSettings : Effect
        data object OpenAppSettings : Effect
        data object RequestLocationPermission : Effect
        data object RequestNotificationPermission : Effect
        data object RequestExactAlarmPermission : Effect

        /** The locale to apply before the app recreates with the new language. */
        data class ApplyLanguage(val languageCode: String) : Effect

        data class ShowError(val message: MessageUi) : Effect
        data object NavigateToHome : Effect
    }
}