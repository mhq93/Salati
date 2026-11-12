package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.MINUTES_PER_DAY
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toMinuteOfDay
import javax.inject.Inject

/**
 * The major prayer whose window is running right now,
 * i.e. the latest of the five daily prayers whose time has already passed.
 *
 * Times before today's Fajr are normalized into the previous day's cycle,
 * so a 03:00 "now" is treated as late in yesterday's timeline rather than before the day began.
 */
class CalculateCurrentPrayerNameUseCase @Inject constructor(
    private val clock: Clock
) {
    /**
     * @param timings the day's prayer timings
     * @return the prayer in progress, or null when no prayer time has passed yet
     */
    operator fun invoke(timings: PrayerTimings): PrayerName? {
        // Anything earlier than Fajr belongs to the tail of the previous day's cycle.
        val fajrMinutes = timings.fajr.toMinuteOfDay()
        fun normalize(minutes: Int) = if (minutes < fajrMinutes) minutes + MINUTES_PER_DAY else minutes
        val nowMinutes = normalize(clock.now().toLocalTime().toMinuteOfDay())

        return PrayerName.majorEntries
            .map { name -> name to normalize(timings[name].toMinuteOfDay()) }
            .filter { (_, minutes) -> minutes <= nowMinutes }
            .maxByOrNull { (_, minutes) -> minutes }
            ?.first
    }
}