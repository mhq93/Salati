package com.mhq.salati.prayertimes.data.mapper

import com.mhq.salati.prayertimes.datasource.network.dto.TimingsDataDto
import com.mhq.salati.prayertimes.datasource.database.PrayerTimesEntity
import com.mhq.salati.prayertimes.domain.model.HijriDate
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.HijriMonth
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**Wire/storage formats. Pinned to Locale.US so digits never depend on the app language.*/
private val DateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)
private val TimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)

/**Shared field-mapping logic, used by both this file and CalendarMapper.kt*/
internal fun TimingsDataDto.toDomainResult(): PrayerTimesResult {
    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = timings.fajr.toPrayerTime(),
            sunrise = timings.sunrise.toPrayerTime(),
            dhuhr = timings.dhuhr.toPrayerTime(),
            asr = timings.asr.toPrayerTime(),
            sunset = timings.sunset.toPrayerTime(),
            maghrib = timings.maghrib.toPrayerTime(),
            isha = timings.isha.toPrayerTime(),
            imsak = timings.imsak.toPrayerTime(),
            midnight = timings.midnight.toPrayerTime(),
            firstThird = timings.firstThird.toPrayerTime(),
            lastThird = timings.lastThird.toPrayerTime()
        ),
        date = PrayerDate(
            gregorianDate = LocalDate.parse(date.gregorian.date, DateFormatter),
            hijriDate = HijriDate(
                day = date.hijri.day.toInt(),
                month = HijriMonth.fromNumber(date.hijri.month.number),
                year = date.hijri.year.toInt()
            )
        )
    )
}

/**domain -> entity (used after either the single-day or calendar path produces a PrayerTimesResult) */
fun PrayerTimesResult.toEntity(
    dateKey: String,
    latitude: Double,
    longitude: Double,
    method: Int,
    schoolId: Int,
    hijriAdjustment: Int
): PrayerTimesEntity {
    return PrayerTimesEntity(
        date = dateKey,
        fajr = timings.fajr.format(TimeFormatter),
        sunrise = timings.sunrise.format(TimeFormatter),
        dhuhr = timings.dhuhr.format(TimeFormatter),
        asr = timings.asr.format(TimeFormatter),
        sunset = timings.sunset.format(TimeFormatter),
        maghrib = timings.maghrib.format(TimeFormatter),
        isha = timings.isha.format(TimeFormatter),
        imsak = timings.imsak.format(TimeFormatter),
        midnight = timings.midnight.format(TimeFormatter),
        firstThird = timings.firstThird.format(TimeFormatter),
        lastThird = timings.lastThird.format(TimeFormatter),
        gregorianDate = date.gregorianDate.format(DateFormatter),
        hijriDate = String.format(
            Locale.US,
            "%02d-%02d-%d",
            date.hijriDate.day,
            date.hijriDate.month.number,
            date.hijriDate.year
        ),
        hijriDay = date.hijriDate.day.toString().padStart(2, '0'),
        hijriMonthNumber = date.hijriDate.month.number,
        hijriYear = date.hijriDate.year.toString(),
        latitude = latitude,
        longitude = longitude,
        method = method,
        schoolId = schoolId,
        hijriAdjustment = hijriAdjustment
    )
}

/** entity -> domain (used for cache hits) */
fun PrayerTimesEntity.toDomain(): PrayerTimesResult {
    return PrayerTimesResult(
        timings = PrayerTimings(
            fajr = fajr.toPrayerTime(),
            sunrise = sunrise.toPrayerTime(),
            dhuhr = dhuhr.toPrayerTime(),
            asr = asr.toPrayerTime(),
            sunset = sunset.toPrayerTime(),
            maghrib = maghrib.toPrayerTime(),
            isha = isha.toPrayerTime(),
            imsak = imsak.toPrayerTime(),
            midnight = midnight.toPrayerTime(),
            firstThird = firstThird.toPrayerTime(),
            lastThird = lastThird.toPrayerTime()
        ),
        date = PrayerDate(
            gregorianDate = LocalDate.parse(gregorianDate, DateFormatter),
            hijriDate = HijriDate(
                day = hijriDay.toInt(),
                month = HijriMonth.fromNumber(hijriMonthNumber),
                year = hijriYear.toInt()
            )
        )
    )
}

/**Aladhan sends e.g. "04:30 (EET)";
 * stored rows are already plain "04:30".
 * Both go through here, and anything that isn't HH:mm now fails loudly
 * instead of silently becoming 00:00.*/
private fun String.toPrayerTime(): LocalTime =
    LocalTime.parse(substringBefore(" (").trim(), TimeFormatter)