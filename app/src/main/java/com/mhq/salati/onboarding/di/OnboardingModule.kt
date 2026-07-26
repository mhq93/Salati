package com.mhq.salati.onboarding.di

import com.mhq.salati.onboarding.data.repoimpl.OnboardingRepoImpl
import com.mhq.salati.onboarding.domain.repo.OnboardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingModule {

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(
        impl: OnboardingRepoImpl
    ): OnboardingRepository
}