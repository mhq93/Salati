package com.mhq.salati.settings.presentation.contract

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.presentation.components.SelectorType
import com.mhq.salati.shared.presentation.components.UiText

object SettingsContract {

    data class State(
        val isLoading: Boolean = true,
        val notificationsEnabled: Boolean = true,
        val calculationMethod: CalculationMethod = CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY,
        val madhab: Madhab = Madhab.SHAFI,
        val themeMode: ThemeMode = ThemeMode.SYSTEM,
        val language: AppLanguage = AppLanguage.ENGLISH,
        val adhanSound: AdhanSound = AdhanSound.DEFAULT,
        val activeSelector: SelectorType? = null,
        val hijriDateOffset: Int = 0,
        val appVersion: String = "",
        val errorMessage: UiText? = null
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
        data class OpenSelector(val type: SelectorType) : Intent
        data object CloseSelector : Intent
        data object RateApp : Intent
        data object ShareApp : Intent
        data object ContactSupport : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
        data object LaunchShareSheet : Effect
        data object OpenPlayStoreListing : Effect
        data object OpenEmailClient : Effect
        data class LanguageChangedRestartRequired(val languageCode: String) : Effect
    }
}