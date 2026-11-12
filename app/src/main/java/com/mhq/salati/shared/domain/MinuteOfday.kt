package com.mhq.salati.shared.domain

import java.time.LocalTime

/** Number of minutes in a full day; the upper bound (exclusive) of any minute-of-day value. */
const val MINUTES_PER_DAY = 24 * 60

/**
 * Converts a time to its minute-of-day value, from 0 (00:00) to 1439 (23:59).
 * Seconds and finer fields are ignored, so a time is identified by its whole minute.
 * This is the unit prayer times are compared and offset in.
 */
fun LocalTime.toMinuteOfDay(): Int = hour * 60 + minute