package com.mhq.salati.home.presentation.model

import java.time.LocalDate

data class PrayerDateUi(
    val gregorianDate: LocalDate,
    val hijriDate: HijriDateUi
)