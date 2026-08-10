package com.mhq.salati.onboarding.domain.repo

interface OnboardingRepository {
    suspend fun hasCompletedOnboarding(): Boolean
    suspend fun setOnboardingCompleted()
}