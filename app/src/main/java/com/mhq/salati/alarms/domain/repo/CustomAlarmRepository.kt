package com.mhq.salati.alarms.domain.repo

import com.mhq.salati.alarms.domain.model.CustomAlarm
import kotlinx.coroutines.flow.Flow

interface CustomAlarmRepository {
    fun observeAlarms(): Flow<List<CustomAlarm>>
    suspend fun getAlarms(): List<CustomAlarm>
    suspend fun getAlarmById(id: Long): CustomAlarm?
    suspend fun createAlarm(alarm: CustomAlarm): Long
    suspend fun updateAlarm(alarm: CustomAlarm)
    suspend fun deleteAlarm(id: Long)
    suspend fun setEnabled(id: Long, enabled: Boolean)
}