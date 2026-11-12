package com.mhq.salati.prayertracker.domain.repo

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

/**
 * Storage of the user's prayer records, implemented in Data.
 *
 * Records are keyed by calendar date and prayer;
 * a prayer with no stored row reads as [PrayerStatus.PENDING].
 * The observe methods emit again whenever the underlying records change.
 */
interface PrayerTrackerRepository {
    /**
     * All records for the days of [yearMonth], keyed by date.
     * Days with no records may be absent from the map.
     */
    fun observeRecordsForMonth(yearMonth: YearMonth):
            Flow<Map<LocalDate, Map<PrayerName, PrayerStatus>>>

    /**
     * All records for [date], keyed by prayer.
     * Prayers with no stored row may be absent from the map.
     */
    fun observeRecordsForDate(date: LocalDate):
            Flow<Map<PrayerName, PrayerStatus>>

    /**
     * One-shot read of every record between [start] and [end], both inclusive.
     * Used by random-access walks (e.g. the streak) that must not re-query per day.
     */
    suspend fun getRecordsForRange(start: LocalDate, end: LocalDate):
            Map<LocalDate, Map<PrayerName, PrayerStatus>>

    /** Writes [status] for [prayer] on [date], replacing any previous value. */
    suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus)
}