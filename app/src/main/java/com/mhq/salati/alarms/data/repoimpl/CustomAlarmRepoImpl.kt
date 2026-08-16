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
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getAlarms(): List<CustomAlarm> = dao.getAll().map { it.toDomain() }

    override suspend fun getAlarmById(id: Long): CustomAlarm? = dao.getById(id)?.toDomain()

    override suspend fun createAlarm(alarm: CustomAlarm): Long = dao.insert(alarm.toEntity())

    override suspend fun updateAlarm(alarm: CustomAlarm) = dao.update(alarm.toEntity())

    override suspend fun deleteAlarm(id: Long) = dao.deleteById(id)

    override suspend fun setEnabled(id: Long, enabled: Boolean) = dao.setEnabled(id, enabled)
}