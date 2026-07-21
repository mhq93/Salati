package com.mhq.salati.domain.model.alarms

data class PrayerAlarm(
    val prayerName: String,
    val triggerAtMillis: Long
)