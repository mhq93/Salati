package com.mhq.salati.onboarding.presentation.contract

class OnboardingContract {
    data class State(
        val currentPage: Int = 0,
        val totalPages: Int = 3,
        val isLoading: Boolean = false
    )

    sealed class Intent {
        object NextPage : Intent()
        object Skip : Intent()
        object Finish : Intent()
    }

    sealed class Effect {
        object NavigateToHome : Effect()
    }
}