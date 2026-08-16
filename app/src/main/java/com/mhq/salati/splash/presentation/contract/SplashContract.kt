package com.mhq.salati.splash.presentation.contract

import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.shared.navigation.Screen

class SplashContract {
    data class State(
        val isLoading: Boolean = true,
        val startDestination: Screen? = null,
        val themeMode: ThemeMode = ThemeMode.SYSTEM
    )

    sealed class Intent {
        object CheckStartDestination : Intent()
    }

    //    sealed class Effect {
    //        data class NavigateTo(val screen: Screen) : Effect()
    //    }
}