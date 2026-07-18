package com.mhq.salati.data.repoimpl

import com.mhq.salati.data.local.PrayerTimesDao
import com.mhq.salati.data.mapper.toDomain
import com.mhq.salati.data.mapper.toEntity
import com.mhq.salati.data.remote.AladhanApiService
import com.mhq.salati.domain.model.PrayerTimesResult
import com.mhq.salati.domain.repo.PrayerTimesRepository

class PrayerTimesRepoImpl(
    private val apiService: AladhanApiService,
    private val dao: PrayerTimesDao
) : PrayerTimesRepository {

    override suspend fun getTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int
    ): Result<PrayerTimesResult> {
        val cached = dao.getByDate(date)
        if (cached != null) {
            return Result.success(cached.toDomain())
        }

        return try {
            val response = apiService.getTimings(date, latitude, longitude, method)
            val domainResult = response.toDomain()
            dao.upsert(domainResult.toEntity(date, latitude, longitude))
            Result.success(domainResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}