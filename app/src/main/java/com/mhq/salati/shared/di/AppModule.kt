package com.mhq.salati.shared.di

import android.content.Context
import androidx.room.Room
import com.mhq.salati.adhan.data.local.MutedPrayerDao
import com.mhq.salati.location.data.LocationProvider
import com.mhq.salati.prayertimes.data.api.AladhanApiService
import com.mhq.salati.prayertimes.data.client.KtorClient
import com.mhq.salati.prayertimes.data.local.PrayerTimesDao
import com.mhq.salati.prayertimes.data.repoimpl.PrayerTimesRepoImpl
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.qibla.data.sensor.CompassProvider
import com.mhq.salati.shared.data.local.SalatiDatabase
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
        apiService: AladhanApiService,
        dao: PrayerTimesDao
    ): PrayerTimesRepository {
        return PrayerTimesRepoImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideSalatiDatabase(
        @ApplicationContext context: Context
    ): SalatiDatabase {
        return Room.databaseBuilder(
            context,
            SalatiDatabase::class.java,
            "salati_database"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    @Singleton
    fun providePrayerTimesDao(database: SalatiDatabase): PrayerTimesDao {
        return database.prayerTimesDao()
    }

    @Provides
    @Singleton
    fun provideLocationProvider(
        @ApplicationContext context: Context
    ): LocationProvider {
        return LocationProvider(context)
    }

    @Provides
    @Singleton
    fun provideCompassProvider(
        @ApplicationContext context: Context
    ): CompassProvider {
        return CompassProvider(context)
    }

    @Provides
    @Singleton
    fun provideMutedPrayerDao(database: SalatiDatabase): MutedPrayerDao {
        return database.mutedPrayerDao()
    }
}