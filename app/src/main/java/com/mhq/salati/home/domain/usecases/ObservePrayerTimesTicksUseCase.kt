package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerTimesTick
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.home.domain.service.DayNavigator
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

/**
 * Counts down [window]; when it ends, either moves on to the next day or starts the next window.
 * Keeps going until the day rolls over, so collect it for as long as these times are on screen.
 *
 * The chain is: countdown ticks until the window ends,
 * then either a day rollover (window crosses midnight past the browsed day)
 * or a status refresh that hands over the next window.
 * A refreshed window that doesn't end later than the one that just ended
 * stops the flow rather than looping forever.
 */
class ObservePrayerTimesTicksUseCase @Inject constructor(
    private val observePrayerCountdownUseCase: ObservePrayerCountdownUseCase,
    private val refreshPrayerStatusUseCase: RefreshPrayerStatusUseCase,
    private val dayNavigator: DayNavigator
) {
    /**
     * @param timings the browsed day's prayer timings
     * @param date the browsed day
     * @param coordinates the location the timings were calculated for
     * @param window the window resolved when these times were loaded
     * @return a flow of ticks that completes on a day rollover or a stalled window
     */
    operator fun invoke(
        timings: PrayerTimings,
        date: LocalDate,
        coordinates: Coordinates,
        window: PrayerWindow
    ): Flow<PrayerTimesTick> = flow {
        var currentWindow = window
        while (true) {
            observePrayerCountdownUseCase(currentWindow).collect { emit(PrayerTimesTick.Countdown(it)) }

            val nextDate = dayNavigator.rolloverDate(date, currentWindow)
            if (nextDate != null) {
                emit(PrayerTimesTick.DayRolledOver(nextDate))
                return@flow
            }

            val status = refreshPrayerStatusUseCase(timings, date, coordinates)
            /** A window that doesn't end later than the one that just ended would loop forever. */
            if (!status.window.end.isAfter(currentWindow.end)) return@flow
            emit(PrayerTimesTick.StatusRefreshed(status))
            currentWindow = status.window
        }
    }
}