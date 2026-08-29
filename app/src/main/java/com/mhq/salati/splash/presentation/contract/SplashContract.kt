package com.mhq.salati.splash.presentation.contract

import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.shared.navigation.Screen
import com.mhq.salati.shared.presentation.components.UiText

class SplashContract {
    data class State(
        val isLoading: Boolean = true,
        val startDestination: Screen? = null,
        val themeMode: ThemeMode = ThemeMode.SYSTEM,
        val isLanguageSelected: Boolean = false,
        val errorMessage: UiText? = null,
        val isDestinationReady: Boolean = false,
        val isSettingsReady: Boolean = false,
    )

    sealed class Intent {
        object CheckStartDestination : Intent()
        data class SelectInitialLanguage(val language: AppLanguage) : Intent()
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
    }
}