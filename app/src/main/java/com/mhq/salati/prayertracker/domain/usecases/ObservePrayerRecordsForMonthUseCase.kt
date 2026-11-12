package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.YearMonth
import javax.inject.Inject

/**
 * Live records for every day of a month, keyed by date; re-emits on changes.
 *
 * Thin pass-through over [PrayerTrackerRepository.observeRecordsForMonth];
 * it is what the calendar's day colors are derived from.
 */
class ObservePrayerRecordsForMonthUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    /** @param yearMonth the month to observe */
    operator fun invoke(yearMonth: YearMonth) = repository.observeRecordsForMonth(yearMonth)
}