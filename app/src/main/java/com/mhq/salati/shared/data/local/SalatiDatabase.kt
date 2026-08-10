package com.mhq.salati.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhq.salati.adhan.data.local.MutedPrayerDao
import com.mhq.salati.adhan.data.local.MutedPrayerEntity
import com.mhq.salati.prayertimes.data.local.PrayerTimesDao
import com.mhq.salati.prayertimes.data.local.PrayerTimesEntity
import com.mhq.salati.prayertracker.data.local.PrayerRecordDao
import com.mhq.salati.prayertracker.data.local.PrayerRecordEntity

@Database(
    entities = [
        PrayerTimesEntity::class,
        MutedPrayerEntity::class,
        PrayerRecordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SalatiDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
    abstract fun mutedPrayerDao(): MutedPrayerDao
    abstract fun prayerRecordDao(): PrayerRecordDao
}