package com.mhq.salati.adhan.domain.model

data class PrayerAlarm(
    val prayerName: String,
    val triggerAtMillis: Long
)