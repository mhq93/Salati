package com.mhq.salati.prayertracker.data.repoimpl

import com.mhq.salati.prayertracker.datasource.database.PrayerRecordDao
import com.mhq.salati.prayertracker.datasource.database.PrayerRecordEntity
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PrayerTrackerRepoImpl @Inject constructor(
    private val prayerRecordDao: PrayerRecordDao
) : PrayerTrackerRepository {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun observeRecordsForMonth(yearMonth: YearMonth) =
        prayerRecordDao.observeForRange(
            yearMonth.atDay(1).format(formatter),
            yearMonth.atEndOfMonth().format(formatter)
        )
            .map { entities -> entities.toMonthMap() }
            .flowOn(Dispatchers.IO)

    override fun observeRecordsForDate(date: LocalDate) =
        prayerRecordDao.observeForDate(date.format(formatter))
            .map { it.toStatusMap() }
            .flowOn(Dispatchers.IO)

    override suspend fun getRecordsForDate(date: LocalDate): Map<PrayerName, PrayerStatus> =
        prayerRecordDao.getForDate(date.format(formatter)).toStatusMap()

    override suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus) {
        prayerRecordDao.upsert(
            PrayerRecordEntity(
                date.format(formatter),
                prayer.storageKey,
                status.name
            )
        )
    }

    private fun List<PrayerRecordEntity>.toStatusMap(): Map<PrayerName, PrayerStatus> =
        mapNotNull { entity ->
            // Skip rows we can't read instead of crashing the whole flow.
            val prayerName = PrayerName.fromStorageKey(entity.prayer) ?: return@mapNotNull null
            val status = runCatching { PrayerStatus.valueOf(entity.status) }.getOrNull()
                ?: return@mapNotNull null
            prayerName to status
        }.toMap()

    private fun List<PrayerRecordEntity>.toMonthMap(): Map<LocalDate, Map<PrayerName, PrayerStatus>> =
        groupBy { LocalDate.parse(it.date, formatter) }
            .mapValues { (_, entries) -> entries.toStatusMap() }

    override suspend fun getRecordsForRange(
        start: LocalDate,
        end: LocalDate
    ): Map<LocalDate, Map<PrayerName, PrayerStatus>> =
        prayerRecordDao.getForRange(start.format(formatter), end.format(formatter)).toMonthMap()
}