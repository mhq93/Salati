package com.mhq.salati.prayertracker.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerRecordDao {
    @Query("SELECT * FROM prayer_records WHERE date BETWEEN :startDate AND :endDate")
    fun observeForRange(startDate: String, endDate: String): Flow<List<PrayerRecordEntity>>

    @Query("SELECT * FROM prayer_records WHERE date = :date")
    fun observeForDate(date: String): Flow<List<PrayerRecordEntity>>

    @Query("SELECT * FROM prayer_records WHERE date = :date")
    suspend fun getForDate(date: String): List<PrayerRecordEntity>

    @Upsert
    suspend fun upsert(record: PrayerRecordEntity)
}