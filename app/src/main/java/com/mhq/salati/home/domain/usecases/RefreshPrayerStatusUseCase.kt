package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerProgress
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Coordinates
import java.time.LocalDate
import javax.inject.Inject

class RefreshPrayerStatusUseCase @Inject constructor(
    private val resolvePrayerWindowForDisplayUseCase: ResolvePrayerWindowForDisplayUseCase,
    private val calculatePrayerStatusUseCase: CalculatePrayerStatusUseCase
) {
    /** Recomputes the status for already-loaded [timings], e.g. when the previous countdown window ends. */
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