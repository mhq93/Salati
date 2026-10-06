package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.HijriMonth
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

fun PrayerDate.applyHijriAdjustment(offsetDays: Int): PrayerDate {
    if (offsetDays == 0) return this

    val adjustedHijriDate = HijrahDate
        .of(hijriDate.year, hijriDate.month.number, hijriDate.day)
        .plus(offsetDays.toLong(), ChronoUnit.DAYS)

    return copy(
        hijriDate = HijriDate(
            day = adjustedHijriDate.get(ChronoField.DAY_OF_MONTH),
            month = HijriMonth.fromNumber(adjustedHijriDate.get(ChronoField.MONTH_OF_YEAR)),
            year = adjustedHijriDate.get(ChronoField.YEAR)
        )
    )
}