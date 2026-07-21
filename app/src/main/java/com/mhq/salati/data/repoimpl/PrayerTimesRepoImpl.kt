package com.mhq.salati.data.repoimpl

import com.mhq.salati.data.local.PrayerTimesDao
import com.mhq.salati.data.mapper.toDomain
import com.mhq.salati.data.mapper.toEntityList
import com.mhq.salati.data.remote.AladhanApiService
import com.mhq.salati.domain.model.prayers.PrayerTimesResult
import com.mhq.salati.domain.repo.PrayerTimesRepository
import kotlin.math.abs

class PrayerTimesRepoImpl(
    private val aladhanApiService: AladhanApiService,
    private val prayerTimesDao: PrayerTimesDao
) : PrayerTimesRepository {

    companion object {
        private const val COORDINATE_TOLERANCE = 0.01
    }

    override suspend fun getCachedTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int
    ): PrayerTimesResult? {
        val cached = prayerTimesDao.getByDate(date)
        val isCacheValid = cached != null &&
                cached.method == method &&
                abs(cached.latitude - latitude) < COORDINATE_TOLERANCE &&
                abs(cached.longitude - longitude) < COORDINATE_TOLERANCE

        return if (isCacheValid) cached!!.toDomain() else null
    }

    override suspend fun getPrayerTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int
    ): Result<PrayerTimesResult> {
        getCachedTimings(date, latitude, longitude, method)?.let {
            return Result.success(it)
        }

        return try {
            val year = date.substringAfterLast("-").toInt()

            val calendarResponse = aladhanApiService.getCalendar(
                year = year,
                latitude = latitude,
                longitude = longitude,
                method = method
            )

            val entities = calendarResponse.toEntityList(latitude, longitude, method)
            prayerTimesDao.insertAll(entities)

            val todayEntity = prayerTimesDao.getByDate(date)
                ?: return Result.failure(
                    IllegalStateException("Requested date not found in fetched calendar")
                )

            Result.success(todayEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}