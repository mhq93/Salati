package com.mhq.salati.onboarding.domain.usecases

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import javax.inject.Inject

/** Marks the onboarding flow as done so it is never shown again. */
class CompleteOnboardingUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke() = onboardingRepository.setOnboardingCompleted()
}