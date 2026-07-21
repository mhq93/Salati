package com.mhq.salati.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhq.salati.data.local.alarms.MutedPrayerDao
import com.mhq.salati.data.local.alarms.MutedPrayerEntity
import com.mhq.salati.data.local.prayers.PrayerTimesDao
import com.mhq.salati.data.local.prayers.PrayerTimesEntity

@Database(
    entities = [
        PrayerTimesEntity::class,
        MutedPrayerEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SalatiDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
    abstract fun mutedPrayerDao(): MutedPrayerDao
}