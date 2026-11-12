package com.mhq.salati.prayertimes.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerTimesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PrayerTimesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<PrayerTimesEntity>)

    @Query("SELECT * FROM prayer_timings WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): PrayerTimesEntity?

    /** Removes every row that was fetched with settings other than the given ones. */
    @Query(
        """
        DELETE FROM prayer_timings
        WHERE method != :method
           OR schoolId != :schoolId
           OR hijriAdjustment != :adjustment
           OR ABS(latitude - :latitude) >= :tolerance
           OR ABS(longitude - :longitude) >= :tolerance
        """
    )
    suspend fun deleteWithOtherSettings(
        method: Int,
        schoolId: Int,
        adjustment: Int,
        latitude: Double,
        longitude: Double,
        tolerance: Double
    )
}