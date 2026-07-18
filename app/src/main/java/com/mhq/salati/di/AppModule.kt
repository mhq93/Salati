package com.mhq.salati.di

import android.content.Context
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.data.remote.AladhanApiService
import com.mhq.salati.data.remote.KtorClient
import com.mhq.salati.data.repoimpl.PrayerTimesRepoImpl
import com.mhq.salati.domain.repo.PrayerTimesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = KtorClient.instance

    @Provides
    @Singleton
    fun provideAladhanApiService(client: HttpClient): AladhanApiService {
        return AladhanApiService(client)
    }

    @Provides
    @Singleton
    fun providePrayerTimesRepository(
        apiService: AladhanApiService
    ): PrayerTimesRepository {
        return PrayerTimesRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideLocationProvider(
        @ApplicationContext context: Context
    ): LocationProvider {
        return LocationProvider(context)
    }
}