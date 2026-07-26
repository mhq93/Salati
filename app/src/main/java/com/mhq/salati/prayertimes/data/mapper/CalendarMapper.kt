package com.mhq.salati.prayertimes.data.mapper

import com.mhq.salati.prayertimes.data.local.PrayerTimesEntity
import com.mhq.salati.prayertimes.data.dto.CalendarResponseDto
import com.mhq.salati.prayertimes.data.dto.TimingsDataDto

fun CalendarResponseDto.toEntityList(
    latitude: Double,
    longitude: Double,
    method: Int
): List<PrayerTimesEntity> {
    return data.values.flatten().map { dayData: TimingsDataDto ->
        val domainResult = dayData.toDomainResult()
        domainResult.toEntity(
            dateKey = dayData.date.gregorian.date,
            latitude = latitude,
            longitude = longitude,
            method = method
        )
    }
}