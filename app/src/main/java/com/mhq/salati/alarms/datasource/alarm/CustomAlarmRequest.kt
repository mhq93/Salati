package com.mhq.salati.alarms.datasource.alarm

/** One custom alarm as the system alarm service needs it. */
data class CustomAlarmRequest(
    val alarmId: Long,
    val label: String,
    val prayerKey: String,
    val offsetMinutes: Int,
    val offsetDirection: String,
    val triggerAtMillis: Long
)