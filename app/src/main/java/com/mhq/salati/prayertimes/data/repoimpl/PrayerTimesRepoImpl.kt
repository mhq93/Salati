package com.mhq.salati.prayertimes.data.repoimpl

import com.mhq.salati.prayertimes.data.mapper.toDomain
import com.mhq.salati.prayertimes.data.mapper.toEntityList
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesDao
import com.mhq.salati.prayertimes.datasource.network.api.AladhanApiService
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.abs

/**
 * Prayer times backed by a Room cache of whole-year Aladhan calendars.
 *
 * A cached day is valid only for the settings it was fetched with
 * (method, madhab, Hijri adjustment, coordinates within [COORDINATE_TOLERANCE]).
 * After a successful fetch, rows from any other settings are purged,
 * so the table only ever holds one consistent settings set.
 */
class PrayerTimesRepoImpl(
    private val aladhanApiService: AladhanApiService,
    private val prayerTimesDao: PrayerTimesDao
) : PrayerTimesRepository {

    companion object {
        private const val COORDINATE_TOLERANCE = 0.01
    }

    /** One annual fetch at a time; callers that waited re-check the cache and skip the network. */
    private val fetchMutex = Mutex()

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

        /** A stored row that no longer parses is treated as a cache miss,
         * so the caller falls back to a fresh fetch (overwrites the bad row) instead of failing. */
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

        return fetchMutex.withLock {
            // Another caller may have filled the cache while this one waited for the lock.
            val filledMeanwhile = getCachedTimings(date, coordinates, method, madhab, adjustment)
            if (filledMeanwhile != null) {
                Result.success(filledMeanwhile)
            } else {
                fetchYearAndPick(date, coordinates, method, madhab, adjustment)
            }
        }
    }

    private suspend fun fetchYearAndPick(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): Result<PrayerTimesResult> {
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

            val entities = calendarResponse.toEntityList(
                coordinates.latitude,
                coordinates.longitude,
                method,
                madhab.schoolId,
                adjustment
            )
            prayerTimesDao.insertAll(entities)
            /** Only after the new rows are safely stored: drop rows from other settings. */
            prayerTimesDao.deleteWithOtherSettings(
                method = method,
                schoolId = madhab.schoolId,
                adjustment = adjustment,
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
                tolerance = COORDINATE_TOLERANCE
            )

            val requested = entities.find { it.date == date }
                ?: return Result.failure(
                    IllegalStateException("Requested date not found in fetched calendar")
                )

            Result.success(requested.toDomain())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}