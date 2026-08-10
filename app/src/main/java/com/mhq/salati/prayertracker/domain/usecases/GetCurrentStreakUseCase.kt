package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.model.PrayerType
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.LocalDate
import javax.inject.Inject

/** Consecutive fully-prayed days ending today;
 * today doesn't break the streak while still "in progress"
 * (no missed prayers logged yet). */

class GetCurrentStreakUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    suspend operator fun invoke(): Int {
        var streak = 0
        var day = LocalDate.now()
        var isToday = true
        while (true) {
            val records = repository.getRecordsForDate(day)
            val allPrayed = PrayerType.entries.all { records[it] == PrayerStatus.PRAYED }
            val hasMissed = records.values.any { it == PrayerStatus.MISSED }
            when {
                allPrayed -> { streak++; day = day.minusDays(1) }
                isToday && !hasMissed -> day = day.minusDays(1)
                else -> break
            }
            isToday = false
        }
        return streak
    }
}