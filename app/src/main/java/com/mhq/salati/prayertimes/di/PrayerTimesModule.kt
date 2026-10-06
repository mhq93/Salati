package com.mhq.salati.prayertimes.di

import com.mhq.salati.prayertimes.datasource.network.api.AladhanApiService
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesDao
import com.mhq.salati.prayertimes.data.repoimpl.PrayerTimesRepoImpl
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrayerTimesModule {

    @Provides
    @Singleton
    fun provideAladhanApiService(client: HttpClient): AladhanApiService {
        return AladhanApiService(client)
    }

    @Provides
    @Singleton
    fun providePrayerTimesDao(database: SalatiDatabase): PrayerTimesDao {
        return database.prayerTimesDao()
    }

    @Provides
    @Singleton
    fun providePrayerTimesRepository(
        apiService: AladhanApiService,
        dao: PrayerTimesDao
    ): PrayerTimesRepository {
        return PrayerTimesRepoImpl(apiService, dao)
    }
}