package com.mhq.salati.prayertracker.di

import com.mhq.salati.prayertracker.datasource.database.PrayerRecordDao
import com.mhq.salati.prayertracker.data.repoimpl.PrayerTrackerRepoImpl
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PrayerTrackerModule {
    @Binds
    abstract fun bindPrayerTrackerRepository(impl: PrayerTrackerRepoImpl): PrayerTrackerRepository

    companion object {
        @Provides
        fun providePrayerRecordDao(db: SalatiDatabase): PrayerRecordDao = db.prayerRecordDao()
    }
}