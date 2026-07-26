package com.mhq.salati.onboarding.domain.usecase

import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import javax.inject.Inject

class SetOnboardingCompletedUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke() = repository.setOnboardingCompleted()
}