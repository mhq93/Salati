package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.AdhanPlaybackControllerImpl
import com.mhq.salati.adhan.data.local.MutedPrayerDao
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import com.mhq.salati.shared.data.local.SalatiDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AdhanModule {

    @Binds
    @Singleton
    abstract fun bindAdhanPlaybackController(
        impl: AdhanPlaybackControllerImpl
    ): AdhanPlaybackController
}