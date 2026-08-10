package com.mhq.salati.prayertimes.data.mapper

import com.mhq.salati.prayertimes.data.dto.TimingsDataDto
import com.mhq.salati.prayertimes.data.local.PrayerTimesEntity
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.model.PrayerTimings

// Shared field-mapping logic, used by both this file and CalendarMapper.kt
internal fun TimingsDataDto.toDomainResult(): PrayerTimesResult {
    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = sanitizeTimestamp(timings.fajr),
            sunrise = sanitizeTimestamp(timings.sunrise),
            dhuhr = sanitizeTimestamp(timings.dhuhr),
            asr = sanitizeTimestamp(timings.asr),
            sunset = sanitizeTimestamp(timings.sunset),
            maghrib = sanitizeTimestamp(timings.maghrib),
            isha = sanitizeTimestamp(timings.isha),
            imsak = sanitizeTimestamp(timings.imsak),
            midnight = sanitizeTimestamp(timings.midnight),
            firstThird = sanitizeTimestamp(timings.firstThird),
            lastThird = sanitizeTimestamp(timings.lastThird)
        ),
        date = PrayerDate(
            readable = date.readable,
            gregorianDate = date.gregorian.date,
            hijriDate = date.hijri.date,
            hijriDay = date.hijri.day,
            hijriMonth = date.hijri.month.en,
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
        hijriDay = date.hijriDay,
        hijriMonth = date.hijriMonth,
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
            hijriDay = hijriDay,
            hijriMonth = hijriMonth,
            hijriYear = hijriYear
        )
    )
}

fun sanitizeTimestamp(raw: String): String {
    return raw.substringBefore(" (").trim()
}