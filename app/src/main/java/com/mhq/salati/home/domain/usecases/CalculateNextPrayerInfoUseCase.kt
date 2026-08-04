package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.NextPrayerInfo
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.usecases.ParseToEpochMillisUseCase
import kotlinx.coroutines.withTimeoutOrNull
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class CalculateNextPrayerInfoUseCase @Inject constructor(
    private val parseToEpochMillisUseCase: ParseToEpochMillisUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase
) {
    suspend operator fun invoke(
        timings: PrayerTimings,
        date: String,
        latitude: Double,
        longitude: Double,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): NextPrayerInfo {
        val prayerMap = listOf(
            "Fajr" to timings.fajr,
            "Dhuhr" to timings.dhuhr,
            "Asr" to timings.asr,
            "Maghrib" to timings.maghrib,
            "Isha" to timings.isha
        )
        val nowMillis = System.currentTimeMillis()
        val millisList = prayerMap.map { (name, time) ->
            name to parseToEpochMillisUseCase(date, time, zoneId)
        }

        val nextIndex = millisList.indexOfFirst { it.second > nowMillis }

        return when {
            nextIndex == -1 -> {
                val tomorrow = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .plusDays(1)
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                NextPrayerInfo(
                    name = "Fajr",
                    spanStartMillis = millisList.last().second,
                    spanEndMillis = parseToEpochMillisUseCase(tomorrow, timings.fajr, zoneId),
                    crossesIntoNextDay = true
                )
            }

            nextIndex == 0 -> {
                val yesterday = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .minusDays(1)
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

                val yesterdayIshaMillis = resolveYesterdayIshaMillis(
                    yesterday = yesterday,
                    latitude = latitude,
                    longitude = longitude,
                    zoneId = zoneId,
                    fallback = timings.isha // last-resort approximation, only if both cache and network fail
                )

                NextPrayerInfo(
                    name = "Fajr",
                    spanStartMillis = yesterdayIshaMillis,
                    spanEndMillis = millisList[0].second,
                    crossesIntoNextDay = false
                )
            }

            else -> {
                val (nextName, nextMillis) = millisList[nextIndex]
                val prevMillis = millisList[nextIndex - 1].second
                NextPrayerInfo(nextName, prevMillis, nextMillis, crossesIntoNextDay = false)
            }
        }
    }

    private suspend fun resolveYesterdayIshaMillis(
        yesterday: String,
        latitude: Double,
        longitude: Double,
        zoneId: ZoneId,
        fallback: String
    ): Long {
        val cached = getCachedPrayerTimesUseCase(yesterday, latitude, longitude)
        if (cached != null) {
            return parseToEpochMillisUseCase(yesterday, cached.timings.isha, zoneId)
        }

        val fetched = withTimeoutOrNull(5_000L.milliseconds) {
            getPrayerTimesUseCase(yesterday, latitude, longitude).getOrNull()
        }
        if (fetched != null) {
            return parseToEpochMillisUseCase(yesterday, fetched.timings.isha, zoneId)
        }

        return parseToEpochMillisUseCase(yesterday, fallback, zoneId)
    }
}