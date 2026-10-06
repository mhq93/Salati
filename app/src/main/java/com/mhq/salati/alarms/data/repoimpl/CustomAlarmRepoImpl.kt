package com.mhq.salati.alarms.data.repoimpl

import com.mhq.salati.alarms.data.mapper.toDomain
import com.mhq.salati.alarms.data.mapper.toEntity
import com.mhq.salati.alarms.datasource.database.CustomAlarmDao
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomAlarmRepoImpl @Inject constructor(
    private val customAlarmDao: CustomAlarmDao
) : CustomAlarmRepository {
    override fun observeAlarms(): Flow<List<CustomAlarm>> =
        customAlarmDao.observeAllAlarms().map { list -> list.mapNotNull { it.toDomain() } }

    override suspend fun getAlarms(): List<CustomAlarm> =
        customAlarmDao.getAllAlarms().mapNotNull { it.toDomain() }

    override suspend fun getAlarmById(id: Long): CustomAlarm? =
        customAlarmDao.getAlarmById(id)?.toDomain()

    override suspend fun createAlarm(alarm: CustomAlarm): Long =
        customAlarmDao.insertAlarm(alarm.toEntity())

    override suspend fun updateAlarm(alarm: CustomAlarm) =
        customAlarmDao.updateAlarm(alarm.toEntity())

    override suspend fun deleteAlarm(id: Long) = customAlarmDao.deleteAlarmById(id)

    override suspend fun setAlarmEnabled(id: Long, enabled: Boolean) =
        customAlarmDao.setAlarmEnabled(id, enabled)
}