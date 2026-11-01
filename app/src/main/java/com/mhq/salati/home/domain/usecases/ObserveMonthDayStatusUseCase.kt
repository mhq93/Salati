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

/** How each recorded day of [yearMonth] went, for the calendar colours. */
class ObserveMonthDayStatusUseCase @Inject constructor(
    private val observePrayerRecordsForMonthUseCase: ObservePrayerRecordsForMonthUseCase,
    private val clock: Clock
) {
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