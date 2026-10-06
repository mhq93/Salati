package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow

interface MutedPrayersRepository {
    suspend fun getMutedPrayers(date: String): Set<PrayerName>
    suspend fun toggleMute(date: String, prayerName: PrayerName, muted: Boolean)
    fun observeMutedPrayers(date: String): Flow<Set<PrayerName>>
    suspend fun purgePastDates()
}