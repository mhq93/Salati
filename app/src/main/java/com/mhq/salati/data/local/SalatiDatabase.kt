package com.mhq.salati.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PrayerTimesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SalatiDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
}