package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

/**
 * The major prayers the user may mark on [date].
 *
 * - Past days: all of them.
 * - Future days: none.
 * - Today: only prayers whose time has arrived. If today's timings can't be found
 *   (no saved location or nothing cached), every prayer is allowed rather than blocking the user.
 */
class GetDuePrayersUseCase @Inject constructor(
    private val clock: Clock,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    suspend operator fun invoke(date: LocalDate): Set<PrayerName> {
        val allPrayers = PrayerName.majorEntries.toSet()
        val today = clock.today()

        return when {
            date.isAfter(today) -> emptySet()
            date.isBefore(today) -> allPrayers
            else -> dueToday(allPrayers, today)
        }
    }

    private suspend fun dueToday(allPrayers: Set<PrayerName>, today: LocalDate): Set<PrayerName> {
        val location = getSavedLocationUseCase().first() ?: return allPrayers
        val timings = getCachedPrayerTimesUseCase(today.toDateKey(), location.coordinates)
            ?.timings ?: return allPrayers

        val timeNow = clock.now().toLocalTime()
        return allPrayers.filterTo(mutableSetOf()) { prayer -> !timeNow.isBefore(timings[prayer]) }
    }
}