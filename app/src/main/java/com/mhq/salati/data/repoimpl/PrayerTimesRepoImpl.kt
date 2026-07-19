package com.mhq.salati.data.repoimpl

import com.mhq.salati.data.local.PrayerTimesDao
import com.mhq.salati.data.mapper.toDomain
import com.mhq.salati.data.mapper.toEntityList
import com.mhq.salati.data.remote.AladhanApiService
import com.mhq.salati.domain.model.prayers.PrayerTimesResult
import com.mhq.salati.domain.repo.PrayerTimesRepository
import kotlin.math.abs

class PrayerTimesRepoImpl(
    private val apiService: AladhanApiService,
    private val dao: PrayerTimesDao
) : PrayerTimesRepository {

    companion object {
        // ~0.01 degrees is roughly 1km at the equator — generous enough to absorb
        // GPS drift between fixes, tight enough to catch a real city/location change.
        private const val COORDINATE_TOLERANCE = 0.01
    }

    override suspend fun getTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int
    ): Result<PrayerTimesResult> {
        val cached = dao.getByDate(date)

        val isCacheValid = cached != null &&
                cached.method == method &&
                abs(cached.latitude - latitude) < COORDINATE_TOLERANCE &&
                abs(cached.longitude - longitude) < COORDINATE_TOLERANCE

        if (isCacheValid) {
            return Result.success(cached!!.toDomain())
        }

        return try {
            val year = date.substringAfterLast("-").toInt()

            val calendarResponse = apiService.getCalendar(
                year = year,
                latitude = latitude,
                longitude = longitude,
                method = method
            )

            val entities = calendarResponse.toEntityList(latitude, longitude, method)
            dao.insertAll(entities)

            val todayEntity = dao.getByDate(date)
                ?: return Result.failure(
                    IllegalStateException("Requested date not found in fetched calendar")
                )

            Result.success(todayEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}