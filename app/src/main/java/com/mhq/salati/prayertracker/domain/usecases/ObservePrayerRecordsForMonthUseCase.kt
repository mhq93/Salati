package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.YearMonth
import javax.inject.Inject

class ObservePrayerRecordsForMonthUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    operator fun invoke(yearMonth: YearMonth) = repository.observeRecordsForMonth(yearMonth)
}