package com.mhq.salati.alarms.domain.model

import com.mhq.salati.shared.domain.PrayerName

data class CustomAlarm(
    val id: Long = 0L,
    val prayerName: PrayerName,
    val label: String,
    val offsetMinutes: Int,
    val offsetDirection: OffsetDirection,
    val everyDay: Boolean,
    val activeDays: Set<Int>,
    val isEnabled: Boolean = true
)