package com.mhq.salati.home.presentation.mapper

import com.mhq.salati.home.presentation.model.HijriDateUi
import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.mapper.toUi

fun PrayerDate.toUi(): PrayerDateUi = PrayerDateUi(
    gregorianDate = gregorianDate,
    hijriDate = HijriDateUi(
        day = hijriDate.day,
        month = hijriDate.month.toUi(),
        year = hijriDate.year
    )
)

/** One entry per domain PrayerName, and PrayerName <-> PrayerNameUi is exhaustive,
 * so every PrayerNameUi has a time. */
fun PrayerTimings.toUi(): PrayerTimingsUi = PrayerTimingsUi(
    times = PrayerName.entries.associate { name -> name.toUi() to this[name] }
)