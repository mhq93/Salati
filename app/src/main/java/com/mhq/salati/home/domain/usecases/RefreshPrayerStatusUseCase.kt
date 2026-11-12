package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerProgress
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Coordinates
import java.time.LocalDate
import javax.inject.Inject

/**
 * Recomputes the status for already-loaded [timings],
 * e.g. when the previous countdown window ends.
 *
 * Re-resolves the display window (which may have moved to the next prayer)
 * and rebuilds the progress from it, without touching cache or network for the browsed day's timings.
 */
class RefreshPrayerStatusUseCase @Inject constructor(
    private val resolvePrayerWindowForDisplayUseCase: ResolvePrayerWindowForDisplayUseCase,
    private val calculatePrayerStatusUseCase: CalculatePrayerStatusUseCase
) {
    /**
     * @param timings the already-loaded timings for the browsed day
     * @param browsedDate the day currently on screen
     * @param coordinates the location the timings were calculated for
     * @return fresh progress: new window, current prayer and past prayers
     */
    suspend operator fun invoke(
        timings: PrayerTimings,
        browsedDate: LocalDate,
        coordinates: Coordinates
    ): PrayerProgress {
        val window = resolvePrayerWindowForDisplayUseCase(
            browsedTimings = timings,
            browsedDate = browsedDate,
            coordinates = coordinates
        )
        return calculatePrayerStatusUseCase(timings, browsedDate, window)
    }
}