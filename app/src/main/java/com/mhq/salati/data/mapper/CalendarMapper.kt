package com.mhq.salati.data.mapper

import com.mhq.salati.data.local.PrayerTimesEntity
import com.mhq.salati.data.remote.dto.CalendarResponseDto
import com.mhq.salati.data.remote.dto.TimingsDataDto

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