package com.mhq.salati.data.repoimpl

import com.mhq.salati.data.mapper.toDomain
import com.mhq.salati.data.remote.AladhanApiService
import com.mhq.salati.domain.model.PrayerTimesResult
import com.mhq.salati.domain.repo.PrayerTimesRepository

class PrayerTimesRepoImpl(
    private val apiService: AladhanApiService
) : PrayerTimesRepository {

    override suspend fun getTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int
    ): Result<PrayerTimesResult> {
        return try {
            val response = apiService.getTimings(
                date,
                latitude,
                longitude,
                method
            )
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}