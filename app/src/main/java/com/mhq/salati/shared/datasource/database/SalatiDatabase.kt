package com.mhq.salati.shared.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhq.salati.adhan.datasource.database.MutedPrayerDao
import com.mhq.salati.adhan.datasource.database.MutedPrayerEntity
import com.mhq.salati.alarms.datasource.database.CustomAlarmDao
import com.mhq.salati.alarms.datasource.database.CustomAlarmEntity
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesDao
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesEntity
import com.mhq.salati.prayertracker.datasource.database.PrayerRecordDao
import com.mhq.salati.prayertracker.datasource.database.PrayerRecordEntity

@Database(
    entities = [
        PrayerTimesEntity::class,
        MutedPrayerEntity::class,
        PrayerRecordEntity::class,
        CustomAlarmEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SalatiDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
    abstract fun mutedPrayerDao(): MutedPrayerDao
    abstract fun prayerRecordDao(): PrayerRecordDao
    abstract fun customAlarmDao(): CustomAlarmDao
}