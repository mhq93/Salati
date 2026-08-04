package com.mhq.salati.adhan.domain.repo

import kotlinx.coroutines.flow.Flow

interface MutedPrayersRepository {
    suspend fun getMutedPrayers(date: String): Set<String>
    suspend fun toggleMute(date: String, prayerName: String, muted: Boolean)
    fun observeMutedPrayers(date: String): Flow<Set<String>>
    suspend fun purgePastDates()
}