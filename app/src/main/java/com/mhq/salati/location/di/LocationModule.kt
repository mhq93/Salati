package com.mhq.salati.location.di

import android.content.Context
import com.mhq.salati.location.data.repoimpl.GeocoderProviderImpl
import com.mhq.salati.location.data.repoimpl.LocationProviderImpl
import com.mhq.salati.location.data.repoimpl.LocationRepoImpl
import com.mhq.salati.location.datasource.device.FusedGpsDataSource
import com.mhq.salati.location.datasource.device.GpsDataSource
import com.mhq.salati.location.datasource.network.service.KtorNominatimApi
import com.mhq.salati.location.datasource.network.service.NominatimApi
import com.mhq.salati.location.datasource.preferences.LocationDataStore
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
        impl: LocationProviderImpl
    ): LocationProvider

    @Binds
    @Singleton
    abstract fun bindGpsDataSource(
        impl: FusedGpsDataSource
    ): GpsDataSource

    @Binds
    @Singleton
    abstract fun bindGeocoderProvider(
        impl: GeocoderProviderImpl
    ): GeocoderProvider

    @Binds
    @Singleton
    abstract fun bindNominatimApi(
        impl: KtorNominatimApi
    ): NominatimApi

    companion object {
        @Provides
        @Singleton
        fun provideLocationDataStore(
            @ApplicationContext context: Context
        ): LocationDataStore = LocationDataStore(context)
    }
}