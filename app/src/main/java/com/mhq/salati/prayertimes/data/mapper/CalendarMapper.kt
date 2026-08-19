package com.mhq.salati.prayertimes.data.mapper

import com.mhq.salati.prayertimes.data.dto.CalendarResponseDto
import com.mhq.salati.prayertimes.data.dto.TimingsDataDto
import com.mhq.salati.prayertimes.data.local.PrayerTimesEntity

fun CalendarResponseDto.toEntityList(
    latitude: Double,
    longitude: Double,
    method: Int,
    schoolId: Int,
    hijriAdjustment: Int
): List<PrayerTimesEntity> {
    return data.values.flatten().map { dayData: TimingsDataDto ->
        val domainResult = dayData.toDomainResult()
        domainResult.toEntity(
            dateKey = dayData.date.gregorian.date,
            latitude = latitude,
            longitude = longitude,
            method = method,
            schoolId = schoolId,
            hijriAdjustment = hijriAdjustment
        )
    }
}