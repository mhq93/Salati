package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalDate
import javax.inject.Inject

class GetCurrentStreakUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    suspend operator fun invoke(): Int {
        var streak = 0
        var day = LocalDate.now()
        var isToday = true
        while (true) {
            val records = repository.getRecordsForDate(day)
            val allPrayed = PrayerName.majorEntries.all { records[it] == PrayerStatus.PRAYED }
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