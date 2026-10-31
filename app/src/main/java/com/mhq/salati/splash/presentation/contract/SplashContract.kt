package com.mhq.salati.splash.presentation.contract

import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.shared.presentation.model.MessageUi
import com.mhq.salati.splash.presentation.model.StartDestinationUi

class SplashContract {
    data class State(
        val isLoading: Boolean = true,
        val startDestination: StartDestinationUi? = null,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val isLanguageSelected: Boolean = false,
        val errorMessage: MessageUi? = null,
        val isDestinationReady: Boolean = false,
        val isSettingsReady: Boolean = false,
    )

    sealed class Intent {
        object CheckStartDestination : Intent()
        data class SelectInitialLanguage(val language: AppLanguageUi) : Intent()
        object LanguageApplied : Intent()
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
        data class ApplyLanguage(val languageCode: String) : Effect
    }
}