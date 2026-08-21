package com.mhq.salati.onboarding.presentation.contract

object OnboardingContract {

    data class State(
        val currentPage: Int = 0,
        val totalPages: Int = 3,
        val isLoading: Boolean = false
    )

    sealed interface Intent {
        data object Load : Intent
        data object NextPage : Intent
        data object Skip : Intent
        data object Finish : Intent
    }

    sealed interface Effect {
        data object NavigateToHome : Effect
    }
}