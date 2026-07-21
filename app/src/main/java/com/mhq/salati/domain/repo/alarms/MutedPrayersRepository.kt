package com.mhq.salati.domain.repo.alarms

import kotlinx.coroutines.flow.Flow

interface MutedPrayersRepository {
    suspend fun getMutedPrayers(): Set<String>
    fun observeMutedPrayers(): Flow<Set<String>>
    suspend fun toggleMute(prayerName: String, muted: Boolean)
}