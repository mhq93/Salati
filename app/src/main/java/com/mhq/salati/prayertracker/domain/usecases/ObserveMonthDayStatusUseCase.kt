package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

/**
 * How each recorded day of [yearMonth] went, for the calendar colors.
 *
 * Mapping per day, first rule that applies wins: a day after today is [DayStatus.FUTURE];
 * any MISSED prayer makes it [DayStatus.HAS_MISSED];
 * all five major prayers PRAYED makes it [DayStatus.ALL_PRAYED];
 * anything else is [DayStatus.IN_PROGRESS].
 *
 * Days with no records at all are absent from the map,
 * so the calendar draws them as unmarked.
 */
class ObserveMonthDayStatusUseCase @Inject constructor(
    private val observePrayerRecordsForMonthUseCase: ObservePrayerRecordsForMonthUseCase,
    private val clock: Clock
) {
    /**
     * @param yearMonth the month to summarize
     * @return a live map of day to status for the days that have records
     */
    operator fun invoke(yearMonth: YearMonth): Flow<Map<LocalDate, DayStatus>> =
        observePrayerRecordsForMonthUseCase(yearMonth).map { recordsByDate ->
            val today = clock.today()
            recordsByDate.mapValues { (date, records) -> dayStatusFor(date, records, today) }
        }

    private fun dayStatusFor(
        date: LocalDate,
        records: Map<PrayerName, PrayerStatus>,
        today: LocalDate
    ): DayStatus = when {
        date.isAfter(today) -> DayStatus.FUTURE
        records.values.any { it == PrayerStatus.MISSED } -> DayStatus.HAS_MISSED
        PrayerName.majorEntries.all { records[it] == PrayerStatus.PRAYED } -> DayStatus.ALL_PRAYED
        else -> DayStatus.IN_PROGRESS
    }
}