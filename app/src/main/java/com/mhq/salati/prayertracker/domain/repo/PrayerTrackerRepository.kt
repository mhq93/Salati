package com.mhq.salati.prayertracker.domain.repo

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.model.PrayerType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface PrayerTrackerRepository {
    fun observeRecordsForMonth(yearMonth: YearMonth): Flow<Map<LocalDate, Map<PrayerType, PrayerStatus>>>
    fun observeRecordsForDate(date: LocalDate): Flow<Map<PrayerType, PrayerStatus>>
    suspend fun getRecordsForDate(date: LocalDate): Map<PrayerType, PrayerStatus>
    suspend fun setStatus(date: LocalDate, prayer: PrayerType, status: PrayerStatus)
}