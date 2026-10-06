package com.mhq.salati.adhan.domain.model

import com.mhq.salati.shared.domain.PrayerName

data class PrayerAlarm(
    val prayerName: PrayerName,
    val triggerAtMillis: Long,
    val isMuted: Boolean
) {
    val isMinorTiming: Boolean get() = prayerName.isMinorTiming
}