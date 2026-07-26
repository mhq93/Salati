package com.mhq.salati.location.di

import android.content.Context
import com.mhq.salati.location.data.LocationProvider
import com.mhq.salati.location.data.local.LocationDataStore
import com.mhq.salati.location.data.repoimpl.LocationRepoImpl
import com.mhq.salati.location.domain.repo.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        impl: LocationRepoImpl
    ): LocationRepository

    companion object {
        @Provides
        @Singleton
        fun provideLocationDataStore(
            @ApplicationContext context: Context
        ): LocationDataStore = LocationDataStore(context)

        @Provides
        @Singleton
        fun provideLocationProvider(
            @ApplicationContext context: Context
        ): LocationProvider = LocationProvider(context)
    }
}