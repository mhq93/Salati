package com.mhq.salati.prayertimes.domain.model

import java.time.LocalDate

data class PrayerDate(
    val gregorianDate: LocalDate,
    val hijriDate: HijriDate
)