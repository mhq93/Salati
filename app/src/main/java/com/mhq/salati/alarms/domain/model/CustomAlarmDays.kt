package com.mhq.salati.alarms.domain.model

import java.util.Calendar

private val AllWeekDays: Set<Int> = (Calendar.SUNDAY..Calendar.SATURDAY).toSet()

/** Days this alarm runs on, as [Calendar] constants. */
private val CustomAlarm.runningDays: Set<Int>
    get() = if (everyDay) AllWeekDays else activeDays

/** Offset from the prayer in minutes: negative before, positive after. */
private val CustomAlarm.signedOffsetMinutes: Int
    get() = if (offsetDirection == OffsetDirection.AFTER) offsetMinutes else -offsetMinutes

/**
 * Returns this alarm with [calendarDay] switched on or off.
 *
 * - Turning off the last remaining day is ignored (the alarm is returned unchanged).
 * - When all seven days end up selected the alarm becomes `everyDay` with an empty day set.
 */
fun CustomAlarm.withDayToggled(calendarDay: Int): CustomAlarm {
    val current = runningDays
    val updated = if (calendarDay in current) current - calendarDay else current + calendarDay

    return when {
        updated.isEmpty() -> this
        updated.size == AllWeekDays.size -> copy(everyDay = true, activeDays = emptySet())
        else -> copy(everyDay = false, activeDays = updated)
    }
}

/**
 * True when this alarm and [other] would fire at the same time on at least one shared day:
 * same prayer, same signed offset (so "0 before" equals "0 after"), and overlapping days.
 */
fun CustomAlarm.conflictsWith(other: CustomAlarm): Boolean =
    prayerName == other.prayerName &&
            signedOffsetMinutes == other.signedOffsetMinutes &&
            runningDays.intersect(other.runningDays).isNotEmpty()