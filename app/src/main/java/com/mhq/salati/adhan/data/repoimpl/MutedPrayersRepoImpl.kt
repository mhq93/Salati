package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.datasource.database.MutedPrayerDao
import com.mhq.salati.adhan.datasource.database.MutedPrayerEntity
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toLocalDateFromKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MutedPrayersRepoImpl @Inject constructor(
    private val mutedPrayerDao: MutedPrayerDao,
    private val clock: Clock
) : MutedPrayersRepository {

    override suspend fun getMutedPrayers(date: String): Set<PrayerName> =
        mutedPrayerDao.getMutedPrayers(date).toPrayerNames()

    override fun observeMutedPrayers(date: String): Flow<Set<PrayerName>> =
        mutedPrayerDao.observeMutedPrayers(date).map { it.toPrayerNames() }

    override suspend fun toggleMute(date: String, prayerName: PrayerName, muted: Boolean) {
        if (muted) {
            val epochDay = date.toLocalDateFromKey().toEpochDay()
            mutedPrayerDao.mute(
                MutedPrayerEntity(date, prayerName.storageKey, epochDay)
            )
        } else {
            mutedPrayerDao.unmute(date, prayerName.storageKey)
        }
    }

    override suspend fun purgePastDates() {
        mutedPrayerDao.purgePast(clock.today().toEpochDay())
    }

    // Rows with a key this version doesn't know are ignored rather than crashing the screen.
    private fun List<String>.toPrayerNames(): Set<PrayerName> =
        mapNotNull { PrayerName.fromStorageKey(it) }.toSet()
}