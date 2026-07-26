package com.mhq.salati.prayertimes.domain.model

data class PrayerDate(
    val readable: String,
    val gregorianDate: String,
    val hijriDate: String,
    val hijriDay: String,
    val hijriMonth: String,
    val hijriYear: String
)