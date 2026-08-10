package com.mhq.salati.prayertracker.di

import com.mhq.salati.prayertracker.data.local.PrayerRecordDao
import com.mhq.salati.prayertracker.data.repoimpl.PrayerTrackerRepositoryImpl
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.data.local.SalatiDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PrayerTrackerModule {
    @Binds
    abstract fun bindPrayerTrackerRepository(impl: PrayerTrackerRepositoryImpl): PrayerTrackerRepository

    companion object {
        @Provides
        fun providePrayerRecordDao(db: SalatiDatabase): PrayerRecordDao = db.prayerRecordDao()
    }
}