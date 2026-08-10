package com.mhq.salati.onboarding.data.repoimpl

import com.mhq.salati.onboarding.data.local.OnboardingDataStore
import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import javax.inject.Inject

class OnboardingRepoImpl @Inject constructor(
    private val dataStore: OnboardingDataStore
) : OnboardingRepository {

    override suspend fun hasCompletedOnboarding(): Boolean {
        return dataStore.hasCompletedOnboarding()
    }

    override suspend fun setOnboardingCompleted() {
        dataStore.setOnboardingCompleted(true)
    }
}