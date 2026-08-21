package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.NextPrayerInfo
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

class ResolveNextPrayerInfoForDisplayUseCase @Inject constructor(
    private val clock: Clock,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val calculateNextPrayerInfoUseCase: CalculateNextPrayerInfoUseCase
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    suspend operator fun invoke(
        browsedTimings: PrayerTimings,
        browsedDate: LocalDate,
        latitude: Double,
        longitude: Double
    ): NextPrayerInfo {
        val browsedDateKey = browsedDate.format(dateKeyFormatter)

        if (browsedDate == clock.today()) {
            return calculateNextPrayerInfoUseCase(browsedTimings, browsedDateKey, latitude, longitude)
        }

        val todayKey = clock.today().format(dateKeyFormatter)
        val todayTimings = getCachedPrayerTimesUseCase(todayKey, latitude, longitude)?.timings
            ?: getPrayerTimesUseCase(todayKey, latitude, longitude).getOrNull()?.timings
            ?: browsedTimings

        val todayInfo = calculateNextPrayerInfoUseCase(todayTimings, todayKey, latitude, longitude)
        val daysOffset = ChronoUnit.DAYS.between(clock.today(), browsedDate)
        val addedMillis = daysOffset * 24L * 60L * 60L * 1000L

        return todayInfo.copy(
            spanStartMillis = todayInfo.spanStartMillis + addedMillis,
            spanEndMillis = todayInfo.spanEndMillis + addedMillis
        )
    }
}