package com.mhq.salati.splash.presentation.contract

import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.splash.presentation.model.StartDestinationUi

class SplashContract {
    data class State(
        val isLoading: Boolean = true,
        val startDestination: StartDestinationUi? = null,
        val themeMode: ThemeModeUi = ThemeModeUi.SYSTEM,
        val isDestinationReady: Boolean = false,
        val isSettingsReady: Boolean = false,
    )

    sealed class Intent {
        object CheckStartDestination : Intent()
    }
}