package com.mhq.salati.data.local.prayers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerTimesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PrayerTimesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<PrayerTimesEntity>)

    @Query("SELECT * FROM prayer_timings WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): PrayerTimesEntity?
}