package com.mhq.salati.data.mapper

import com.mhq.salati.data.local.PrayerTimesEntity
import com.mhq.salati.data.remote.dto.TimingsResponseDto
import com.mhq.salati.domain.model.PrayerDate
import com.mhq.salati.domain.model.PrayerTimesResult
import com.mhq.salati.domain.model.PrayerTimings

fun TimingsResponseDto.toDomain(): PrayerTimesResult {
    val timingsDto = data.timings
    val dateDto = data.date

    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = timingsDto.fajr,
            sunrise = timingsDto.sunrise,
            dhuhr = timingsDto.dhuhr,
            asr = timingsDto.asr,
            sunset = timingsDto.sunset,
            maghrib = timingsDto.maghrib,
            isha = timingsDto.isha,
            imsak = timingsDto.imsak,
            midnight = timingsDto.midnight,
            firstThird = timingsDto.firstThird,
            lastThird = timingsDto.lastThird
        ),
        date = PrayerDate(
            readable = dateDto.readable,
            gregorianDate = dateDto.gregorian.date,
            hijriDate = dateDto.hijri.date,
            hijriMonthName = dateDto.hijri.month.en,
            hijriYear = dateDto.hijri.year
        )
    )
}

fun PrayerTimesResult.toEntity(
    date: String,
    latitude: Double,
    longitude: Double
): PrayerTimesEntity {
    return PrayerTimesEntity(
        date = date,
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
        readableDate = this.date.readable,
        gregorianDate = this.date.gregorianDate,
        hijriDate = this.date.hijriDate,
        hijriMonthName = this.date.hijriMonthName,
        hijriYear = this.date.hijriYear,
        latitude = latitude,
        longitude = longitude
    )
}

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