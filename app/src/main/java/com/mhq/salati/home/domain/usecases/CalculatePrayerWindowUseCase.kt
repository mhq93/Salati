package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.withTimeoutOrNull
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class CalculatePrayerWindowUseCase @Inject constructor(
    private val clock: Clock,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    // Single formatter, always Locale.US, used for both parse and format
    // so the date-key round-trip can't drift under non-Latin-digit locales.
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    suspend operator fun invoke(
        timings: PrayerTimings,
        date: String,
        coordinates: Coordinates
    ): PrayerWindow {
        val zoneId = clock.zone()
        val day = LocalDate.parse(date, dateKeyFormatter)

        val now = clock.instant()
        val prayerInstants = PrayerName.majorEntries.map { name ->
            name to day.instantAt(timings[name], zoneId)
        }

        val nextIndex = prayerInstants.indexOfFirst { (_, instant) -> instant.isAfter(now) }

        return when {
            nextIndex == -1 -> {
                PrayerWindow(
                    nextPrayer = PrayerName.FAJR,
                    start = prayerInstants.last().second,
                    end = day.plusDays(1).instantAt(timings.fajr, zoneId)
                )
            }

            nextIndex == 0 -> {
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

    private suspend fun resolveYesterdayIsha(
        yesterday: LocalDate,
        coordinates: Coordinates,
        zoneId: ZoneId,
        fallback: LocalTime
    ): Instant {
        val yesterdayKey = yesterday.format(dateKeyFormatter)

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

    private fun LocalDate.instantAt(time: LocalTime, zoneId: ZoneId): Instant =
        atTime(time).atZone(zoneId).toInstant()
}