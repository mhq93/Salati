package com.mhq.salati.home.domain.model

import com.mhq.salati.shared.domain.PrayerName

data class NextPrayerInfo(
    val name: PrayerName,
    val spanStartMillis: Long,
    val spanEndMillis: Long,
    val crossesIntoNextDay: Boolean
)