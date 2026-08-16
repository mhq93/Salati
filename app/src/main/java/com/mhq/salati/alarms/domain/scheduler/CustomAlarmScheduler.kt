package com.mhq.salati.alarms.domain.scheduler

import com.mhq.salati.alarms.domain.model.CustomAlarm

interface CustomAlarmScheduler {
    fun schedule(alarm: CustomAlarm, triggerAtMillis: Long)
    fun cancel(alarmId: Long)
}