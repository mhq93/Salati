package com.mhq.salati.presentation.settings

import com.mhq.salati.domain.model.settings.AdhanSound
import com.mhq.salati.domain.model.settings.AppLanguage
import com.mhq.salati.domain.model.settings.CalculationMethod
import com.mhq.salati.domain.model.settings.Madhab
import com.mhq.salati.domain.model.settings.ThemeMode


object SettingsContract {

    data class State(
        val isLoading: Boolean = true,
        val notificationsEnabled: Boolean = true,
        val calculationMethod: CalculationMethod = CalculationMethod.EGYPTIAN,
        val madhab: Madhab = Madhab.SHAFI,
        val themeMode: ThemeMode = ThemeMode.SYSTEM,
        val language: AppLanguage = AppLanguage.ENGLISH,
        val adhanSound: AdhanSound = AdhanSound.DEFAULT,
        val hijriDateOffset: Int = 0,
        val appVersion: String = ""
    )

    sealed interface Intent {
        data class ToggleNotifications(val enabled: Boolean) : Intent
        data class SelectCalculationMethod(val method: CalculationMethod) : Intent
        data class SelectMadhab(val madhab: Madhab) : Intent
        data class SelectTheme(val mode: ThemeMode) : Intent
        data class SelectLanguage(val language: AppLanguage) : Intent
        data class SelectAdhanSound(val sound: AdhanSound) : Intent
        data object IncrementHijriOffset : Intent
        data object DecrementHijriOffset : Intent
        data object RateApp : Intent
        data object ShareApp : Intent
        data object ContactSupport : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        data object LaunchShareSheet : Effect
        data object OpenPlayStoreListing : Effect
        data object OpenEmailClient : Effect
        data object LanguageChangedRestartRequired : Effect
    }
}