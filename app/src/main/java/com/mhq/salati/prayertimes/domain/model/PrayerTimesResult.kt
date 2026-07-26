package com.mhq.salati.prayertimes.domain.model

data class PrayerTimesResult(
    val timings: PrayerTimings,
    val date: PrayerDate
)