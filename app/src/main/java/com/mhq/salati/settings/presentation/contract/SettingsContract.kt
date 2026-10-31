package com.mhq.salati.settings.presentation.contract

import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi

object SettingsContract {

    data class State(
        val isLoading: Boolean = true,
        val notificationsEnabled: Boolean = true,
        val hasNotificationPermission: Boolean = true,
        val calculationMethod: CalculationMethodUi = CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY,
        val madhab: MadhabUi = MadhabUi.SHAFI,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val language: AppLanguageUi = AppLanguageUi.ENGLISH,
        val adhanSound: AdhanSoundUi = AdhanSoundUi.DEFAULT,
        val customAlarmSound: CustomAlarmSoundUi = CustomAlarmSoundUi.DEFAULT,
        val activeSelector: SelectorType? = null,
        val hijriDateOffset: Int = 0,
        val errorMessage: MessageUi? = null,
        val timeFormat: TimeFormatUi = TimeFormatUi.HOUR_24,
    )

    sealed interface Intent {
        data class ToggleNotifications(val enabled: Boolean) : Intent
        data class NotificationPermissionResult(val granted: Boolean) : Intent
        data object RecheckNotificationPermission : Intent
        data class SelectCalculationMethod(val method: CalculationMethodUi) : Intent
        data class SelectMadhab(val madhab: MadhabUi) : Intent
        data class SelectTheme(val mode: ThemeModeUi) : Intent
        data class SelectLanguage(val language: AppLanguageUi) : Intent
        data class SelectAdhanSound(val sound: AdhanSoundUi) : Intent
        data class SelectCustomAlarmSound(val sound: CustomAlarmSoundUi) : Intent
        data object IncrementHijriOffset : Intent
        data object DecrementHijriOffset : Intent
        data class OpenSelector(val type: SelectorType) : Intent
        data object CloseSelector : Intent
        data object RateApp : Intent
        data object ShareApp : Intent
        data object ContactSupport : Intent
        data class SelectTimeFormat(val format: TimeFormatUi) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data class LanguageChangedRestartRequired(val languageCode: String) : Effect
        data object LaunchShareSheet : Effect
        data object OpenPlayStoreListing : Effect
        data object OpenEmailClient : Effect
        data object RequestNotificationPermission : Effect
    }
}