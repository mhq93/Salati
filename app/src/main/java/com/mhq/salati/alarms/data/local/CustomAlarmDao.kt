package com.mhq.salati.alarms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomAlarmDao {
    @Query("SELECT * FROM custom_alarms ORDER BY id DESC")
    fun observeAllAlarms(): Flow<List<CustomAlarmEntity>>

    @Query("SELECT * FROM custom_alarms ORDER BY id DESC")
    suspend fun getAllAlarms(): List<CustomAlarmEntity>

    @Query("SELECT * FROM custom_alarms WHERE id = :id")
    suspend fun getAlarmById(id: Long): CustomAlarmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(entity: CustomAlarmEntity): Long

    @Update
    suspend fun updateAlarm(entity: CustomAlarmEntity)

    @Query("DELETE FROM custom_alarms WHERE id = :id")
    suspend fun deleteAlarmById(id: Long)

    @Query("UPDATE custom_alarms SET isEnabled = :enabled WHERE id = :id")
    suspend fun setAlarmEnabled(id: Long, enabled: Boolean)
}