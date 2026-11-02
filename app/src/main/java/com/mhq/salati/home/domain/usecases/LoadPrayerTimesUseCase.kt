package com.mhq.salati.home.domain.usecases

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val FETCH_TIMEOUT = 5_000L.milliseconds

class LoadPrayerTimesUseCase @Inject constructor(
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val resolvePrayerWindowForDisplayUseCase: ResolvePrayerWindowForDisplayUseCase,    private val calculatePrayerStatusUseCase: CalculatePrayerStatusUseCase,
    private val connectivityChecker: ConnectivityChecker
) {
    /** Cache first. Only on a cache miss does it emit [PrayerTimesLoad.FetchingFromNetwork] and go online. */
    operator fun invoke(date: LocalDate, coordinates: Coordinates): Flow<PrayerTimesLoad> = flow {
        val dateKey = date.toDateKey()

        val cached = getCachedPrayerTimesUseCase(dateKey, coordinates)
        if (cached != null) {
            emit(loaded(cached, date, coordinates))
            return@flow
        }

        emit(PrayerTimesLoad.FetchingFromNetwork)

        val result = withTimeoutOrNull(FETCH_TIMEOUT) {
            getPrayerTimesUseCase(dateKey, coordinates)
        }
        if (result == null) {
            emit(PrayerTimesLoad.Failed(failureWhileOnlineOr(PrayerTimesError.TimedOut)))
            return@flow
        }

        val fetched = result.getOrNull()
        if (fetched != null) {
            emit(loaded(fetched, date, coordinates))
        } else {
            val error = PrayerTimesError.Other(result.exceptionOrNull()?.message)
            emit(PrayerTimesLoad.Failed(failureWhileOnlineOr(error)))
        }
    }

    private suspend fun loaded(
        result: PrayerTimesResult,
        date: LocalDate,
        coordinates: Coordinates
    ): PrayerTimesLoad.Loaded {
        val window = resolvePrayerWindowForDisplayUseCase(result.timings, date, coordinates)
        return PrayerTimesLoad.Loaded(
            timings = result.timings,
            prayerDate = result.date,
            status = calculatePrayerStatusUseCase(result.timings, date, window)
        )
    }

    private fun failureWhileOnlineOr(error: PrayerTimesError): PrayerTimesError =
        if (connectivityChecker.isConnected()) error else PrayerTimesError.Offline
}