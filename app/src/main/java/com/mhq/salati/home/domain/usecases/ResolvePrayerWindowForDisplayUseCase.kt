package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.toDateKey
import java.time.LocalDate
import javax.inject.Inject

/**
 * The window the Home screen should count down, no matter which day is browsed.
 *
 * The countdown always runs to today's real next prayer, whichever day is being browsed.
 * Today's own timings are used when available; the browsed ones are only a last resort.
 * Resolution order for today's timings: the browsed timings when the browsed day is today,
 * then today's cache, then a network fetch, then the browsed timings as a fallback.
 */
class ResolvePrayerWindowForDisplayUseCase @Inject constructor(
    private val clock: Clock,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val calculatePrayerWindowUseCase: CalculatePrayerWindowUseCase
) {
    /**
     * @param browsedTimings the timings already loaded for the browsed day
     * @param browsedDate the day currently on screen
     * @param coordinates the location the timings were calculated for
     * @return the window to today's next prayer, built from today's timings
     */
    suspend operator fun invoke(
        browsedTimings: PrayerTimings,
        browsedDate: LocalDate,
        coordinates: Coordinates
    ): PrayerWindow {
        val today = clock.today()
        val todayKey = today.toDateKey()

        val todayTimings = if (browsedDate == today) {
            browsedTimings
        } else {
            getCachedPrayerTimesUseCase(todayKey, coordinates)?.timings
                ?: getPrayerTimesUseCase(todayKey, coordinates).getOrNull()?.timings
                ?: browsedTimings
        }

        return calculatePrayerWindowUseCase(todayTimings, todayKey, coordinates)
    }
}