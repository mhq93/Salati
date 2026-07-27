package com.mhq.salati.adhan.domain.repo

import kotlinx.coroutines.flow.Flow

interface MutedPrayersRepository {
    suspend fun getMutedPrayers(): Set<String>
    suspend fun toggleMute(prayerName: String, muted: Boolean)
    fun observeMutedPrayers(): Flow<Set<String>>
}