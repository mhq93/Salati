package com.mhq.salati.data.repoimpl.alarms

import com.mhq.salati.data.local.alarms.MutedPrayerDao
import com.mhq.salati.data.local.alarms.MutedPrayerEntity
import com.mhq.salati.domain.repo.alarms.MutedPrayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MutedPrayersRepoImpl @Inject constructor(
    private val mutedPrayerDao: MutedPrayerDao
) : MutedPrayersRepository {

    override suspend fun getMutedPrayers(): Set<String> =
        mutedPrayerDao.getMutedPrayers().toSet()

    override fun observeMutedPrayers(): Flow<Set<String>> =
        mutedPrayerDao.observeMutedPrayers().map { it.toSet() }

    override suspend fun toggleMute(prayerName: String, muted: Boolean) {
        if (muted) {
            mutedPrayerDao.mute(MutedPrayerEntity(prayerName))
        } else {
            mutedPrayerDao.unmute(prayerName)
        }
    }
}