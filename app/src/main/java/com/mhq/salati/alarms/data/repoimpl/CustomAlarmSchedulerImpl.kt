package com.mhq.salati.alarms.data.repoimpl

import com.mhq.salati.alarms.datasource.alarm.CustomAlarmDataSource
import com.mhq.salati.alarms.datasource.alarm.CustomAlarmRequest
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.shared.data.mapper.storageKey
import javax.inject.Inject

class CustomAlarmSchedulerImpl @Inject constructor(
    private val customAlarmDataSource: CustomAlarmDataSource
) : CustomAlarmScheduler {

    override fun schedule(customAlarm: CustomAlarm, triggerAtMillis: Long) {
        customAlarmDataSource.schedule(
            CustomAlarmRequest(
                alarmId = customAlarm.id,
                label = customAlarm.label,
                prayerKey = customAlarm.prayerName.storageKey,
                offsetMinutes = customAlarm.offsetMinutes,
                offsetDirection = customAlarm.offsetDirection.name,
                triggerAtMillis = triggerAtMillis
            )
        )
    }

    override fun cancel(alarmId: Long) {
        customAlarmDataSource.cancel(alarmId)
    }
}