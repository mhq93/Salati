package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.datasource.database.MutedPrayerDao
import com.mhq.salati.adhan.datasource.database.MutedPrayerEntity
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class MutedPrayersRepoImpl @Inject constructor(
    private val mutedPrayerDao: MutedPrayerDao
) : MutedPrayersRepository {

    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    override suspend fun getMutedPrayers(date: String): Set<PrayerName> =
        mutedPrayerDao.getMutedPrayers(date).toPrayerNames()

    override fun observeMutedPrayers(date: String): Flow<Set<PrayerName>> =
        mutedPrayerDao.observeMutedPrayers(date).map { it.toPrayerNames() }

    override suspend fun toggleMute(date: String, prayerName: PrayerName, muted: Boolean) {
        if (muted) {
            val epochDay = LocalDate.parse(date, dateKeyFormatter).toEpochDay()
            mutedPrayerDao.mute(
                MutedPrayerEntity(date, prayerName.storageKey, epochDay)
            )
        } else {
            mutedPrayerDao.unmute(date, prayerName.storageKey)
        }
    }

    override suspend fun purgePastDates() {
        val todayEpoch = LocalDate.now().toEpochDay()
        mutedPrayerDao.purgePast(todayEpoch)
    }

    // Rows with a key this version doesn't know are ignored rather than crashing the screen.
    private fun List<String>.toPrayerNames(): Set<PrayerName> =
        mapNotNull { PrayerName.fromStorageKey(it) }.toSet()
}