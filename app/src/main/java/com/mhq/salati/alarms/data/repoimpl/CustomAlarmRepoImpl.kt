package com.mhq.salati.alarms.data.repoimpl

import com.mhq.salati.alarms.data.local.CustomAlarmDao
import com.mhq.salati.alarms.data.local.toDomain
import com.mhq.salati.alarms.data.local.toEntity
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomAlarmRepoImpl @Inject constructor(
    private val dao: CustomAlarmDao
) : CustomAlarmRepository {

    override fun observeAlarms(): Flow<List<CustomAlarm>> =
        dao.observeAllAlarms().map { list -> list.map { it.toDomain() } }

    override suspend fun getAlarms(): List<CustomAlarm> = dao.getAllAlarms().map { it.toDomain() }

    override suspend fun getAlarmById(id: Long): CustomAlarm? = dao.getAlarmById(id)?.toDomain()

    override suspend fun createAlarm(alarm: CustomAlarm): Long = dao.insertAlarm(alarm.toEntity())

    override suspend fun updateAlarm(alarm: CustomAlarm) = dao.updateAlarm(alarm.toEntity())

    override suspend fun deleteAlarm(id: Long) = dao.deleteAlarmById(id)

    override suspend fun setAlarmEnabled(id: Long, enabled: Boolean) = dao.setAlarmEnabled(id, enabled)
}