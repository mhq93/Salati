package com.mhq.salati.prayertimes.domain.model

import java.time.LocalDate

/**
 * The two calendars a prayer day is shown in.
 *
 * @property gregorianDate the civil date the timings were calculated for
 * @property hijriDate the Hijri date displayed alongside it
 * (already adjusted for the user's offset by the time it reaches the UI)
 */
data class PrayerDate(
    val gregorianDate: LocalDate,
    val hijriDate: HijriDate
)