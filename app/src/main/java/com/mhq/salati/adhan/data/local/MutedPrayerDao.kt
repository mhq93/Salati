package com.mhq.salati.adhan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MutedPrayerDao {
    @Query("SELECT prayerName FROM muted_prayers WHERE date = :date")
    suspend fun getMutedPrayers(date: String): List<String>

    @Query("SELECT prayerName FROM muted_prayers WHERE date = :date")
    fun observeMutedPrayers(date: String): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun mute(entity: MutedPrayerEntity)

    @Query("DELETE FROM muted_prayers WHERE date = :date AND prayerName = :prayerName")
    suspend fun unmute(date: String, prayerName: String)

    @Query("DELETE FROM muted_prayers WHERE epochDay < :beforeEpochDay")
    suspend fun purgePast(beforeEpochDay: Long)
}