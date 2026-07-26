package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.data.local.MutedPrayerDao
import com.mhq.salati.adhan.data.local.MutedPrayerEntity
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
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