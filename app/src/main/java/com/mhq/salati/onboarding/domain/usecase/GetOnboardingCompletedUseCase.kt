package com.mhq.salati.onboarding.domain.usecase

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import javax.inject.Inject

class GetOnboardingCompletedUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(): Boolean = repository.hasCompletedOnboarding()
}