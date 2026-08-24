package com.mhq.salati.location.di

import android.content.Context
import com.mhq.salati.location.data.AndroidLocationProvider
import com.mhq.salati.location.data.NominatimGeocoderProvider
import com.mhq.salati.location.data.datastore.LocationDataStore
import com.mhq.salati.location.data.repoimpl.LocationRepoImpl
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.location.domain.repo.LocationProvider
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

    @Binds
    @Singleton
    abstract fun bindLocationProvider(
        impl: AndroidLocationProvider
    ): LocationProvider

    @Binds
    @Singleton
    abstract fun bindGeocoderProvider(
        impl: NominatimGeocoderProvider
    ): GeocoderProvider

    companion object {
        @Provides
        @Singleton
        fun provideLocationDataStore(
            @ApplicationContext context: Context
        ): LocationDataStore = LocationDataStore(context)
    }
}