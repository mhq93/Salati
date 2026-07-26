package com.mhq.salati.qibla.di

import android.content.Context
import com.mhq.salati.qibla.data.sensor.CompassProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QiblaModule {

    @Provides
    @Singleton
    fun provideCompassProvider(
        @ApplicationContext context: Context
    ): CompassProvider {
        return CompassProvider(context)
    }
}