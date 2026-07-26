package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.LocalDate
import javax.inject.Inject

class ObservePrayerRecordsForDateUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    operator fun invoke(date: LocalDate) = repository.observeRecordsForDate(date)
}