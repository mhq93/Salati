package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalDate
import javax.inject.Inject

/**
 * Records the user's marking for one prayer on one day.
 *
 * Thin pass-through over [PrayerTrackerRepository.setStatus];
 * whether the marking is allowed (the prayer is due) is decided upstream by GetDuePrayersUseCase.
 */
class SetPrayerStatusUseCase @Inject constructor(
    private val prayerTrackerRepository: PrayerTrackerRepository
) {
    /**
     * @param date the day being marked
     * @param prayer the prayer being marked
     * @param status the new status, replacing any previous one
     */
    suspend operator fun invoke(date: LocalDate, prayer: PrayerName, status: PrayerStatus) {
        prayerTrackerRepository.setStatus(date, prayer, status)
    }
}