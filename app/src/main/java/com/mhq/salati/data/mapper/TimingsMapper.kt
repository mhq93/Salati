package com.mhq.salati.data.mapper


import com.mhq.salati.data.local.PrayerTimesEntity
import com.mhq.salati.data.remote.dto.TimingsDataDto
import com.mhq.salati.data.remote.dto.TimingsResponseDto
import com.mhq.salati.domain.model.prayers.PrayerDate
import com.mhq.salati.domain.model.prayers.PrayerTimesResult
import com.mhq.salati.domain.model.prayers.PrayerTimings

// Single-day response -> delegates to the shared helper below
fun TimingsResponseDto.toDomain(): PrayerTimesResult = data.toDomainResult()

// Shared field-mapping logic, used by both this file and CalendarMapper.kt
internal fun TimingsDataDto.toDomainResult(): PrayerTimesResult {
    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = timings.fajr,
            sunrise = timings.sunrise,
            dhuhr = timings.dhuhr,
            asr = timings.asr,
            sunset = timings.sunset,
            maghrib = timings.maghrib,
            isha = timings.isha,
            imsak = timings.imsak,
            midnight = timings.midnight,
            firstThird = timings.firstThird,
            lastThird = timings.lastThird
        ),
        date = PrayerDate(
            readable = date.readable,
            gregorianDate = date.gregorian.date,
            hijriDate = date.hijri.date,
            hijriMonthName = date.hijri.month.en,
            hijriYear = date.hijri.year
        )
    )
}

// domain -> entity (used after either the single-day or calendar path produces a PrayerTimesResult)
fun PrayerTimesResult.toEntity(
    dateKey: String,
    latitude: Double,
    longitude: Double,
    method: Int
): PrayerTimesEntity {
    return PrayerTimesEntity(
        date = dateKey,
        fajr = timings.fajr,
        sunrise = timings.sunrise,
        dhuhr = timings.dhuhr,
        asr = timings.asr,
        sunset = timings.sunset,
        maghrib = timings.maghrib,
        isha = timings.isha,
        imsak = timings.imsak,
        midnight = timings.midnight,
        firstThird = timings.firstThird,
        lastThird = timings.lastThird,
        readableDate = date.readable,
        gregorianDate = date.gregorianDate,
        hijriDate = date.hijriDate,
        hijriMonthName = date.hijriMonthName,
        hijriYear = date.hijriYear,
        latitude = latitude,
        longitude = longitude,
        method = method
    )
}

// entity -> domain (used for cache hits)
fun PrayerTimesEntity.toDomain(): PrayerTimesResult {
    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = fajr,
            sunrise = sunrise,
            dhuhr = dhuhr,
            asr = asr,
            sunset = sunset,
            maghrib = maghrib,
            isha = isha,
            imsak = imsak,
            midnight = midnight,
            firstThird = firstThird,
            lastThird = lastThird
        ),
        date = PrayerDate(
            readable = readableDate,
            gregorianDate = gregorianDate,
            hijriDate = hijriDate,
            hijriMonthName = hijriMonthName,
            hijriYear = hijriYear
        )
    )
}