package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerProgress
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import javax.inject.Inject

class CalculatePrayerStatusUseCase @Inject constructor(
    private val clock: Clock,
    private val calculateCurrentPrayerNameUseCase: CalculateCurrentPrayerNameUseCase,
    private val calculatePastPrayersUseCase: CalculatePastPrayersUseCase
) {
    /** The current and past prayers only mean something for today, so other days get none. */
    operator fun invoke(timings: PrayerTimings, date: LocalDate, window: PrayerWindow): PrayerProgress {
        val isToday = date == clock.today()
        return PrayerProgress(
            window = window,
            currentPrayer = if (isToday) calculateCurrentPrayerNameUseCase(timings) else null,
            pastPrayers = if (isToday) calculatePastPrayersUseCase(timings) else emptySet()
        )
    }
}