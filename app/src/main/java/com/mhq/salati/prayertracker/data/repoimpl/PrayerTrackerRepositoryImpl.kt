package com.mhq.salati.prayertracker.data.repoimpl

import com.mhq.salati.prayertracker.data.local.PrayerRecordDao
import com.mhq.salati.prayertracker.data.local.PrayerRecordEntity
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PrayerTrackerRepositoryImpl @Inject constructor(
    private val prayerRecordDao: PrayerRecordDao
) : PrayerTrackerRepository {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun observeRecordsForMonth(yearMonth: YearMonth) =
        prayerRecordDao.observeForRange(
            yearMonth.atDay(1).format(formatter),
            yearMonth.atEndOfMonth().format(formatter)
        ).map { entities -> entities.toMonthMap() }

    override fun observeRecordsForDate(date: LocalDate) =
        prayerRecordDao.observeForDate(date.format(formatter)).map { it.toStatusMap() }

    override suspend fun getRecordsForDate(date: LocalDate): Map<PrayerName, PrayerStatus> =
        prayerRecordDao.getForDate(date.format(formatter)).toStatusMap()

    override suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus) {
        prayerRecordDao.upsert(PrayerRecordEntity(date.format(formatter), prayer.name, status.name))
    }

    private fun List<PrayerRecordEntity>.toStatusMap(): Map<PrayerName, PrayerStatus> =
        associate { PrayerName.valueOf(it.prayer) to PrayerStatus.valueOf(it.status) }

    private fun List<PrayerRecordEntity>.toMonthMap(): Map<LocalDate, Map<PrayerName, PrayerStatus>> =
        groupBy { LocalDate.parse(it.date, formatter) }
            .mapValues { (_, entries) -> entries.toStatusMap() }
}