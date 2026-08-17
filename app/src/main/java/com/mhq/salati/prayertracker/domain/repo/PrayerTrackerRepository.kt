package com.mhq.salati.prayertracker.domain.repo

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface PrayerTrackerRepository {
    fun observeRecordsForMonth(yearMonth: YearMonth): Flow<Map<LocalDate, Map<PrayerName, PrayerStatus>>>
    fun observeRecordsForDate(date: LocalDate): Flow<Map<PrayerName, PrayerStatus>>
    suspend fun getRecordsForDate(date: LocalDate): Map<PrayerName, PrayerStatus>
    suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus)
}