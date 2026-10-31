package com.mhq.salati.splash.domain.usecases

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import com.mhq.salati.splash.domain.model.StartDestination
import javax.inject.Inject

class GetStartDestinationUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) {
    suspend operator fun invoke(): StartDestination {
        if (!onboardingRepository.hasCompletedOnboarding()) return StartDestination.ONBOARDING
        return StartDestination.HOME
    }
}