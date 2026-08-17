package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalDate
import javax.inject.Inject

class SetPrayerStatusUseCase @Inject constructor(
    private val repository: PrayerTrackerRepository
) {
    suspend operator fun invoke(date: LocalDate, prayer: PrayerName, status: PrayerStatus) {
        repository.setStatus(date, prayer, status)
    }
}