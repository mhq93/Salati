package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.HijriMonth
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

/**
 * Shifts a date's Hijri part by [offsetDays] while leaving the Gregorian part alone.
 *
 * Users can nudge it when local moon sighting differs from the computed one.
 * A zero offset resets the date otherwise the date is re-derived from the adjusted [HijrahDate],
 * rolling over months and years as needed.
 *
 * @receiver the date whose Hijri part to adjust
 * @param offsetDays days to add (negative to subtract)
 * @return a copy of the date with the adjusted Hijri part
 */
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