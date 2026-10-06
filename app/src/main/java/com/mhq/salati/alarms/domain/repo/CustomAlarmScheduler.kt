package com.mhq.salati.alarms.domain.repo

import com.mhq.salati.alarms.domain.model.CustomAlarm

interface CustomAlarmScheduler {
    fun schedule(customAlarm: CustomAlarm, triggerAtMillis: Long)
    fun cancel(alarmId: Long)
}