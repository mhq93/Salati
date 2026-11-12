package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import com.mhq.salati.shared.domain.toLocalDateFromKey
import kotlinx.coroutines.withTimeoutOrNull
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/**
 * The [PrayerWindow] a day's timings put us in right now.
 *
 * Three cases:
 * - midday: the window between the last passed prayer and the next one;
 * - after Isha: a window from Isha to tomorrow's Fajr;
 * - before Fajr: a window from yesterday's Isha to today's Fajr.
 * Yesterday's Isha is taken from cache first, then a short network fetch,
 * then today's Isha as a fallback, so the window is never blocked on the network.
 */
class CalculatePrayerWindowUseCase @Inject constructor(
    private val clock: Clock,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    /**
     * @param timings the day's prayer timings
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location the timings were calculated for
     * @return the window that contains the current moment
     */
    suspend operator fun invoke(
        timings: PrayerTimings,
        date: String,
        coordinates: Coordinates
    ): PrayerWindow {
        val zoneId = clock.zone()
        val day = date.toLocalDateFromKey()

        val now = clock.instant()
        val prayerInstants = PrayerName.majorEntries.map { name ->
            name to day.instantAt(timings[name], zoneId)
        }

        val nextIndex = prayerInstants.indexOfFirst { (_, instant) -> instant.isAfter(now) }

        return when (nextIndex) {
            -1 -> {
                PrayerWindow(
                    nextPrayer = PrayerName.FAJR,
                    start = prayerInstants.last().second,
                    end = day.plusDays(1).instantAt(timings.fajr, zoneId)
                )
            }
            0 -> {
                val yesterdayIsha = resolveYesterdayIsha(
                    yesterday = day.minusDays(1),
                    coordinates = coordinates,
                    zoneId = zoneId,
                    fallback = timings.isha
                )

                PrayerWindow(
                    nextPrayer = PrayerName.FAJR,
                    start = yesterdayIsha,
                    end = prayerInstants[0].second
                )
            }
            else -> {
                val (nextName, nextInstant) = prayerInstants[nextIndex]
                val previousInstant = prayerInstants[nextIndex - 1].second
                PrayerWindow(nextName, previousInstant, nextInstant)
            }
        }
    }

    /**
     * Yesterday's Isha instant, resolved from the cheapest source that works:
     * cache, then a 5-second-bounded network fetch,
     * then today's Isha as a fallback (close enough for the window's start).
     */
    private suspend fun resolveYesterdayIsha(
        yesterday: LocalDate,
        coordinates: Coordinates,
        zoneId: ZoneId,
        fallback: LocalTime
    ): Instant {
        val yesterdayKey = yesterday.toDateKey()

        val cached = getCachedPrayerTimesUseCase(yesterdayKey, coordinates)
        if (cached != null) {
            return yesterday.instantAt(cached.timings.isha, zoneId)
        }

        val fetched = withTimeoutOrNull(5_000L.milliseconds) {
            getPrayerTimesUseCase(yesterdayKey, coordinates).getOrNull()
        }
        if (fetched != null) {
            return yesterday.instantAt(fetched.timings.isha, zoneId)
        }

        return yesterday.instantAt(fallback, zoneId)
    }

    /** The instant [time] occurs on this day in [zoneId]. */
    private fun LocalDate.instantAt(time: LocalTime, zoneId: ZoneId): Instant =
        atTime(time).atZone(zoneId).toInstant()
}