package com.mhq.salati.di

import android.content.Context
import androidx.room.Room
import com.mhq.salati.data.local.PrayerTimesDao
import com.mhq.salati.data.local.SalatiDatabase
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
}