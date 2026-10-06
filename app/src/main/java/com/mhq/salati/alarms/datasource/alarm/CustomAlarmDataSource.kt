package com.mhq.salati.alarms.datasource.alarm

interface CustomAlarmDataSource {
    fun schedule(request: CustomAlarmRequest)
    fun cancel(alarmId: Long)
}