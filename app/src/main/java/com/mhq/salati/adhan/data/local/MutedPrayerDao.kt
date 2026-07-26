package com.mhq.salati.adhan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MutedPrayerDao {
    @Query("SELECT prayerName FROM muted_prayers")
    suspend fun getMutedPrayers(): List<String>

    @Query("SELECT prayerName FROM muted_prayers")
    fun observeMutedPrayers(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun mute(entity: MutedPrayerEntity)

    @Query("DELETE FROM muted_prayers WHERE prayerName = :prayerName")
    suspend fun unmute(prayerName: String)
}