package com.mhq.salati.di.location

import android.content.Context
import com.mhq.salati.data.local.location.LocationDataStore
import com.mhq.salati.data.repoimpl.location.LocationRepositoryImpl
import com.mhq.salati.domain.repo.location.LocationRepository
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
        impl: LocationRepositoryImpl
    ): LocationRepository

    companion object {
        @Provides
        @Singleton
        fun provideLocationDataStore(
            @ApplicationContext context: Context
        ): LocationDataStore = LocationDataStore(context)
    }
}