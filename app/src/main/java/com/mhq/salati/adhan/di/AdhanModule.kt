package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.repoimpl.AdhanPlaybackRepoImpl
import com.mhq.salati.adhan.datasource.playback.AdhanPlaybackDataSource
import com.mhq.salati.adhan.datasource.playback.AndroidAdhanPlaybackDataSource
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import com.mhq.salati.adhan.ui.service.AdhanPlaybackService
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AdhanModule {

    @Binds
    @Singleton
    abstract fun bindAdhanPlaybackController(
        impl: AdhanPlaybackRepoImpl
    ): AdhanPlaybackController

    // Singleton: it holds the playback status shared by the service and every observer.
    @Binds
    @Singleton
    abstract fun bindAdhanPlaybackDataSource(
        impl: AndroidAdhanPlaybackDataSource
    ): AdhanPlaybackDataSource

    companion object {
        @Provides
        @Named("adhan_playback_service")
        fun provideAdhanPlaybackService(): ComponentTarget = ComponentTarget(AdhanPlaybackService::class.java)
    }
}