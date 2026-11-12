package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * Live records for one day, keyed by prayer; re-emits whenever they change.
 *
 * Thin pass-through over [PrayerTrackerRepository.observeRecordsForDate];
 * exists so presentation code goes through the standard use-case seam.
 */
class ObservePrayerRecordsForDateUseCase @Inject constructor(
    private val prayerTrackerRepository: PrayerTrackerRepository
) {
    /** @param date the day to observe */
    operator fun invoke(date: LocalDate) = prayerTrackerRepository.observeRecordsForDate(date)
}