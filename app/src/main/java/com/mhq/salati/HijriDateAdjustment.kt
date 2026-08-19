package com.mhq.salati

import com.mhq.salati.prayertimes.domain.model.PrayerDate
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

fun PrayerDate.applyHijriAdjustment(offsetDays: Int): PrayerDate {
    if (offsetDays == 0) return this

    val hijrahDate = HijrahDate.of(hijriYear.toInt(), hijriMonthNumber, hijriDay.toInt())
    val adjusted = hijrahDate.plus(offsetDays.toLong(), ChronoUnit.DAYS)

    val day = adjusted.get(ChronoField.DAY_OF_MONTH)
    val month = adjusted.get(ChronoField.MONTH_OF_YEAR)
    val year = adjusted.get(ChronoField.YEAR)

    return copy(
        hijriDay = day.toString().padStart(2, '0'),
        hijriMonthNumber = month,
        hijriYear = year.toString(),
        hijriDate = "${day.toString().padStart(2, '0')}-${month.toString().padStart(2, '0')}-$year"
    )
}