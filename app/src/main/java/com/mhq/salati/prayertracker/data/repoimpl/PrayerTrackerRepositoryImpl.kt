package com.mhq.salati.prayertracker.data.repoimpl

import com.mhq.salati.prayertracker.data.local.PrayerRecordDao
import com.mhq.salati.prayertracker.data.local.PrayerRecordEntity
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.model.PrayerType
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PrayerTrackerRepositoryImpl @Inject constructor(
    private val dao: PrayerRecordDao
) : PrayerTrackerRepository {

    private val fmt = DateTimeFormatter.ISO_LOCAL_DATE

    override fun observeRecordsForMonth(yearMonth: YearMonth) =
        dao.observeForRange(
            yearMonth.atDay(1).format(fmt),
            yearMonth.atEndOfMonth().format(fmt)
        ).map { entities -> entities.toMonthMap() }

    override fun observeRecordsForDate(date: LocalDate) =
        dao.observeForDate(date.format(fmt)).map { it.toStatusMap() }

    override suspend fun getRecordsForDate(date: LocalDate): Map<PrayerType, PrayerStatus> =
        dao.getForDate(date.format(fmt)).toStatusMap()

    override suspend fun setStatus(date: LocalDate, prayer: PrayerType, status: PrayerStatus) {
        dao.upsert(PrayerRecordEntity(date.format(fmt), prayer.name, status.name))
    }

    private fun List<PrayerRecordEntity>.toStatusMap(): Map<PrayerType, PrayerStatus> =
        associate { PrayerType.valueOf(it.prayer) to PrayerStatus.valueOf(it.status) }

    private fun List<PrayerRecordEntity>.toMonthMap(): Map<LocalDate, Map<PrayerType, PrayerStatus>> =
        groupBy { LocalDate.parse(it.date, fmt) }
            .mapValues { (_, entries) -> entries.toStatusMap() }
}