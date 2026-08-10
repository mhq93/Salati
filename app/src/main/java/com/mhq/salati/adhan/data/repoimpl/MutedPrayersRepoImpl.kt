package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.data.local.MutedPrayerDao
import com.mhq.salati.adhan.data.local.MutedPrayerEntity
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MutedPrayersRepoImpl @Inject constructor(
    private val mutedPrayerDao: MutedPrayerDao
) : MutedPrayersRepository {

    override suspend fun getMutedPrayers(date: String): Set<String> =
        mutedPrayerDao.getMutedPrayers(date).toSet()

    override fun observeMutedPrayers(date: String): Flow<Set<String>> =
        mutedPrayerDao.observeMutedPrayers(date).map { it.toSet() }

    override suspend fun toggleMute(date: String, prayerName: String, muted: Boolean) {
        if (muted) {
            val epochDay = LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
            ).toEpochDay()
            mutedPrayerDao.mute(
                MutedPrayerEntity(date, prayerName, epochDay)
            )
        } else {
            mutedPrayerDao.unmute(date, prayerName)
        }
    }

    override suspend fun purgePastDates() {
        val todayEpoch = LocalDate.now().toEpochDay()
        mutedPrayerDao.purgePast(todayEpoch)
    }
}