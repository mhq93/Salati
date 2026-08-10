package com.mhq.salati.home.domain.model

data class NextPrayerInfo(
    val name: String,
    val spanStartMillis: Long,
    val spanEndMillis: Long,
    val crossesIntoNextDay: Boolean
)