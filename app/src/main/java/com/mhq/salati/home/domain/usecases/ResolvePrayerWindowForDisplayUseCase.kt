package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

class ResolvePrayerWindowForDisplayUseCase @Inject constructor(
    private val clock: Clock,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val calculatePrayerWindowUseCase: CalculatePrayerWindowUseCase
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    suspend operator fun invoke(
        browsedTimings: PrayerTimings,
        browsedDate: LocalDate,
        coordinates: Coordinates
    ): PrayerWindow {
        val browsedDateKey = browsedDate.format(dateKeyFormatter)

        if (browsedDate == clock.today()) {
            return calculatePrayerWindowUseCase(browsedTimings, browsedDateKey, coordinates)
        }

        val todayKey = clock.today().format(dateKeyFormatter)
        val todayTimings = getCachedPrayerTimesUseCase(todayKey, coordinates)?.timings
            ?: getPrayerTimesUseCase(todayKey, coordinates).getOrNull()?.timings
            ?: browsedTimings

        val todayWindow = calculatePrayerWindowUseCase(todayTimings, todayKey, coordinates)
        val daysOffset = ChronoUnit.DAYS.between(clock.today(), browsedDate)

        return todayWindow.plusDays(daysOffset, clock.zone())
    }
}