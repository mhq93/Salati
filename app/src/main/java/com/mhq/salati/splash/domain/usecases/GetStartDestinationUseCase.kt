package com.mhq.salati.splash.domain.usecases

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import com.mhq.salati.shared.ui.navigation.NavigationScreen
import javax.inject.Inject

class GetStartDestinationUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) {
    suspend operator fun invoke(): NavigationScreen {
        if (!onboardingRepository.hasCompletedOnboarding()) return NavigationScreen.Onboarding
        return NavigationScreen.Home
    }
}