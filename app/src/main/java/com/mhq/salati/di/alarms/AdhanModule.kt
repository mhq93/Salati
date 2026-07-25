package com.mhq.salati.di.alarms

import com.mhq.salati.data.alarms.AdhanPlaybackControllerImpl
import com.mhq.salati.domain.repo.alarms.AdhanPlaybackController
import dagger.Binds
import dagger.Module
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