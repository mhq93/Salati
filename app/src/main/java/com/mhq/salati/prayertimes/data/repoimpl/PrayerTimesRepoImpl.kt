package com.mhq.salati.prayertimes.data.repoimpl

import com.mhq.salati.prayertimes.datasource.network.api.AladhanApiService
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesDao
import com.mhq.salati.prayertimes.data.mapper.toDomain
import com.mhq.salati.prayertimes.data.mapper.toEntityList
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.shared.domain.Coordinates
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
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): PrayerTimesResult? {
        val cached = prayerTimesDao.getByDate(date)
        val isCacheValid = cached != null &&
                cached.method == method &&
                cached.schoolId == madhab.schoolId &&
                cached.hijriAdjustment == adjustment &&
                abs(cached.latitude - coordinates.latitude) < COORDINATE_TOLERANCE &&
                abs(cached.longitude - coordinates.longitude) < COORDINATE_TOLERANCE

        // A stored row that no longer parses is treated as a cache miss, so the caller falls
        // through to a fresh fetch (which overwrites the bad row) instead of failing.
        return if (isCacheValid) runCatching { cached!!.toDomain() }.getOrNull() else null
    }

    override suspend fun getPrayerTimings(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): Result<PrayerTimesResult> {
        getCachedTimings(date, coordinates, method, madhab, adjustment)?.let {
            return Result.success(it)
        }

        return try {
            val year = date.substringAfterLast("-").toInt()

            val calendarResponse = aladhanApiService.getCalendar(
                year = year,
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
                method = method,
                school = madhab.schoolId,
                adjustment = adjustment
            )

            val entities = calendarResponse.toEntityList(coordinates.latitude, coordinates.longitude, method, madhab.schoolId, adjustment)
            prayerTimesDao.insertAll(entities)

            // Fixed: pull today's entity straight out of the list we just mapped from the
            // API response instead of round-tripping Room for data we already have in memory.
            val todayEntity = entities.find { it.date == date }
                ?: return Result.failure(
                    IllegalStateException("Requested date not found in fetched calendar")
                )

            Result.success(todayEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}