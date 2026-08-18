package com.mhq.salati.alarms.domain.scheduler

import com.mhq.salati.alarms.domain.model.CustomAlarm

interface CustomAlarmScheduler {
    fun schedule(customAlarm: CustomAlarm, triggerAtMillis: Long)
    fun cancel(alarmId: Long)
}