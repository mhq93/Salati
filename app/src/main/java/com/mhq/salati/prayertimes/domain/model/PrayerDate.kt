package com.mhq.salati.prayertimes.domain.model

data class PrayerDate(
    val gregorianDate: String,
    val hijriDate: String,
    val hijriDay: String,
    val hijriMonthNumber: Int,
    val hijriYear: String
)