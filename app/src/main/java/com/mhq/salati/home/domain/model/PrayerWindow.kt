package com.mhq.salati.home.domain.model

import com.mhq.salati.shared.domain.PrayerName
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * The stretch of time leading up to [nextPrayer]:
 * it starts at the previous prayer time
 * and ends when [nextPrayer] begins.
 * Home's countdown ring and timer are drawn from it.
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

    fun remainingAt(now: Instant): Duration =
        Duration.between(now, end).coerceAtLeast(Duration.ZERO)

    /** 0f when [now] is at (or before) the start,
     * 1f at (or after) the end. */
    fun progressAt(now: Instant): Float {
        val totalMillis = Duration.between(start, end).toMillis()
        if (totalMillis <= 0L) return 0f
        val elapsedMillis = Duration.between(start, now).toMillis()
        return (elapsedMillis.toFloat() / totalMillis.toFloat()).coerceIn(0f, 1f)
    }

    /** True when this window ends on a calendar day later than [day]
     * (e.g. after Isha, running into tomorrow's Fajr). */
    fun endsAfterDay(day: LocalDate, zoneId: ZoneId): Boolean =
        end.atZone(zoneId).toLocalDate().isAfter(day)

    /** Moves the whole window by calendar days (not by 24h blocks),
     * so daylight-saving changes don't skew it. */
    fun plusDays(days: Long, zoneId: ZoneId): PrayerWindow = copy(
        start = start.atZone(zoneId).plusDays(days).toInstant(),
        end = end.atZone(zoneId).plusDays(days).toInstant()
    )
}