package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import javax.inject.Inject

/**
 * How many consecutive days (ending today or yesterday) the user prayed all five daily prayers.
 *
 * Today is special: it is still in progress,
 * so it counts toward the streak only if all prayers are marked;
 * being unmarked doesn't break the streak, but a MISSED prayer does.
 */
class GetCurrentStreakUseCase @Inject constructor(
    private val prayerTrackerRepository: PrayerTrackerRepository,
    private val clock: Clock
) {
    /**
     * @return the current streak length in days;
     * 0 when today (or the most recent recorded day) already breaks it
     */
    suspend operator fun invoke(): Int {
        var streak = 0
        var day = clock.today()
        var isToday = true

        /** Walk back one window at a time and stop at the first day that breaks the streak:
         * a short streak reads one small window, a long one reads only as much as it needs,
         * and there is no artificial cap. A day with no records always ends the walk. */
        while (true) {
            val windowStart = day.minusDays(WINDOW_DAYS - 1)
            val records = prayerTrackerRepository.getRecordsForRange(windowStart, day)

            while (day >= windowStart) {
                val dayRecords = records[day].orEmpty()
                val allPrayed = PrayerName.majorEntries.all { dayRecords[it] == PrayerStatus.PRAYED }
                val hasMissed = dayRecords.values.any { it == PrayerStatus.MISSED }

                when {
                    allPrayed -> streak++
                    /** Today in progress: doesn't break the streak unless something was missed. */
                    isToday && !hasMissed -> Unit
                    else -> return streak
                }
                isToday = false
                day = day.minusDays(1)
            }
        }
    }

    /** Days fetched per repository call; a batching detail, not a streak cap. */
    private companion object {
        const val WINDOW_DAYS = 90L
    }
}