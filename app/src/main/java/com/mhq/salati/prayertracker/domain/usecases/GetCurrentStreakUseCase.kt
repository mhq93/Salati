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
        var streak = 0
        var day = clock.today()
        var isToday = true

        // Walk back one window at a time and stop at the first day that breaks the streak:
        // a short streak reads one small window, a long one reads only as much as it needs,
        // and there is no artificial cap. A day with no records always ends the walk.
        while (true) {
            val windowStart = day.minusDays(WINDOW_DAYS - 1)
            val records = prayerTrackerRepository.getRecordsForRange(windowStart, day)

            while (day >= windowStart) {
                val dayRecords = records[day].orEmpty()
                val allPrayed = PrayerName.majorEntries.all { dayRecords[it] == PrayerStatus.PRAYED }
                val hasMissed = dayRecords.values.any { it == PrayerStatus.MISSED }

                when {
                    allPrayed -> streak++
                    // Today is still in progress: it doesn't break the streak unless something was missed.
                    isToday && !hasMissed -> Unit
                    else -> return streak
                }
                isToday = false
                day = day.minusDays(1)
            }
        }
    }

    private companion object {
        const val WINDOW_DAYS = 90L
    }
}