package com.mhq.salati.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<OnboardingContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: OnboardingContract.Intent) {
        when (intent) {
            is OnboardingContract.Intent.NextPage -> handleNextPage()
            is OnboardingContract.Intent.Skip -> completeOnboarding()
            is OnboardingContract.Intent.Finish -> completeOnboarding()
        }
    }

    private fun handleNextPage() {
        val current = _state.value.currentPage
        val last = _state.value.totalPages - 1

        if (current < last) {
            _state.update { it.copy(currentPage = current + 1) }
        } else {
            completeOnboarding()
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            setOnboardingCompletedUseCase()
            _effect.send(OnboardingContract.Effect.NavigateToHome)
        }
    }
}