package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.model.PrayerType
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import java.time.LocalDate
import javax.inject.Inject

class SetPrayerStatusUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    suspend operator fun invoke(date: LocalDate, prayer: PrayerType, status: PrayerStatus) {
        repository.setStatus(date, prayer, status)
    }
}