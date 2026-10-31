package com.mhq.salati.splash.presentation.contract

import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.shared.ui.UiText
import com.mhq.salati.splash.presentation.model.StartDestinationUi

class SplashContract {
    data class State(
        val isLoading: Boolean = true,
        val startDestination: StartDestinationUi? = null,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val isLanguageSelected: Boolean = false,
        val errorMessage: UiText? = null,
        val isDestinationReady: Boolean = false,
        val isSettingsReady: Boolean = false,
    )

    sealed class Intent {
        object CheckStartDestination : Intent()
        data class SelectInitialLanguage(val language: AppLanguageUi) : Intent()
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
    }
}