package com.mhq.salati.onboarding.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.viewmodel.OnboardingViewModel

@Composable
fun OnboardingContainer(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val state by onboardingViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        onboardingViewModel.effect.collect { effect ->
            when (effect) {
                is OnboardingContract.Effect.NavigateToHome -> onFinished()
            }
        }
    }

    OnboardingContent(
        state = state,
        onIntent = onboardingViewModel::onIntent
    )
}