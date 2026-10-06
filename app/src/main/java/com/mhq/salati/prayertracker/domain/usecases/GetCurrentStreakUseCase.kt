package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import javax.inject.Inject

class GetCurrentStreakUseCase @Inject constructor(
    private val prayerTrackerRepository: PrayerTrackerRepository,
    private val clock: Clock
) {
    suspend operator fun invoke(): Int {
        val today = clock.today()
        // CHANGED — was 365, which was capping real multi-year streaks.
        val startDate = today.minusDays(MAX_STREAK_LOOKBACK_DAYS)

        val recordsMap = prayerTrackerRepository.getRecordsForRange(startDate, today)

        var streak = 0
        var day = today
        var isToday = true

        while (day >= startDate) {
            val records = recordsMap[day] ?: emptyMap()
            val allPrayed = PrayerName.majorEntries.all { records[it] == PrayerStatus.PRAYED }
            val hasMissed = records.values.any { it == PrayerStatus.MISSED }

            when {
                allPrayed -> {
                    streak++
                    day = day.minusDays(1)
                }
                isToday && !hasMissed -> {
                    day = day.minusDays(1)
                }
                else -> break
            }
            isToday = false
        }
        return streak
    }

    private companion object {
        const val MAX_STREAK_LOOKBACK_DAYS = 3_650L // NEW — ~10 years, was 365
    }
}