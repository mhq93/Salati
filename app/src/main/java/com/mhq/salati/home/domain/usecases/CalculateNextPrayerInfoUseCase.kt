package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.NextPrayerInfo
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.usecases.ParseToEpochMillisUseCase
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalculateNextPrayerInfoUseCase @Inject constructor(
    private val parseToEpochMillisUseCase: ParseToEpochMillisUseCase
) {
    operator fun invoke(
        timings: PrayerTimings,
        date: String,
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
        return if (nextIndex == -1) {
            val tomorrow = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            NextPrayerInfo(
                name = "Fajr",
                spanStartMillis = millisList.last().second,
                spanEndMillis = parseToEpochMillisUseCase(tomorrow, timings.fajr, zoneId),
                crossesIntoNextDay = true
            )
        } else {
            val (nextName, nextMillis) = millisList[nextIndex]
            val prevMillis = if (nextIndex == 0) millisList[0].second else millisList[nextIndex - 1].second
            NextPrayerInfo(nextName, prevMillis, nextMillis, crossesIntoNextDay = false)
        }
    }
}