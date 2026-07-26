package com.mhq.salati.splash.presentation.contract

import com.mhq.salati.shared.navigation.Screen

class SplashContract {
    data class State(
        val isLoading: Boolean = true
    )

    sealed class Intent {
        object CheckStartDestination : Intent()
    }

    sealed class Effect {
        data class NavigateTo(val screen: Screen) : Effect()
    }
}