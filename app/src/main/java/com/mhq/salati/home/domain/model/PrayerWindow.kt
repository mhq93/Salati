package com.mhq.salati.home.domain.model

import com.mhq.salati.shared.domain.PrayerName
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * The stretch of time leading up to [nextPrayer]:
 * it starts at the previous prayer time and ends when [nextPrayer] begins.
 * Home's countdown ring and timer are drawn from it.
 *
 * Validated on construction: [end] may equal [start] (an instant window) but never precede it.
 *
 * @property nextPrayer the prayer whose time [end] points at
 * @property start the instant the window opens, i.e. the previous prayer's time
 * @property end the instant [nextPrayer] begins
 * @throws IllegalArgumentException if [end] is before [start]
 */
data class PrayerWindow(
    val nextPrayer: PrayerName,
    val start: Instant,
    val end: Instant
) {
    init {
        require(!end.isBefore(start)) {
            "A prayer window cannot end before it starts: $start > $end"
        }
    }

    /**
     * Time left until [end] as seen from [now];
     * clamped to zero once the window has ended, so callers never see a negative countdown.
     */
    fun remainingAt(now: Instant): Duration =
        Duration.between(now, end).coerceAtLeast(Duration.ZERO)

    /**
     * How far through the window [now] sits,
     * from 0f at (or before) the start to 1f at (or after) the end.
     * A zero-length window is always 0f.
     */
    fun progressAt(now: Instant): Float {
        val totalMillis = Duration.between(start, end).toMillis()
        if (totalMillis <= 0L) return 0f
        val elapsedMillis = Duration.between(start, now).toMillis()
        return (elapsedMillis.toFloat() / totalMillis.toFloat()).coerceIn(0f, 1f)
    }

    /**
     * True when this window ends on a calendar day later than [day]
     * (e.g. after Isha, running into tomorrow's Fajr);
     * the signal for a day rollover when the browsed day is [day].
     */
    fun endsAfterDay(day: LocalDate, zoneId: ZoneId): Boolean =
        end.atZone(zoneId).toLocalDate().isAfter(day)
}