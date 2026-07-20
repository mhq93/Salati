package com.mhq.salati.domain.model.prayers

data class PrayerDate(
    val readable: String,
    val gregorianDate: String,
    val hijriDate: String,
    val hijriDay: String,
    val hijriMonth: String,
    val hijriYear: String
)