package com.mhq.salati.data.mapper

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
            firstThird = timingsDto.firstthird,
            lastThird = timingsDto.lastthird
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