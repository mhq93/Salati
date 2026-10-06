package com.mhq.salati.home.domain.usecases

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val FETCH_TIMEOUT = 5_000L.milliseconds

class LoadPrayerTimesUseCase @Inject constructor(
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val calculatePrayerWindowUseCase: CalculatePrayerWindowUseCase,
    private val calculatePrayerStatusUseCase: CalculatePrayerStatusUseCase,
    private val connectivityChecker: ConnectivityChecker
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    /** Cache first. Only on a cache miss does it emit [PrayerTimesLoad.FetchingFromNetwork] and go online. */
    operator fun invoke(date: LocalDate, coordinates: Coordinates): Flow<PrayerTimesLoad> = flow {
        val dateKey = date.format(dateKeyFormatter)

        val cached = getCachedPrayerTimesUseCase(dateKey, coordinates)
        if (cached != null) {
            emit(loaded(cached, date, dateKey, coordinates))
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
            emit(loaded(fetched, date, dateKey, coordinates))
        } else {
            val error = PrayerTimesError.Other(result.exceptionOrNull()?.message)
            emit(PrayerTimesLoad.Failed(failureWhileOnlineOr(error)))
        }
    }

    private suspend fun loaded(
        result: PrayerTimesResult,
        date: LocalDate,
        dateKey: String,
        coordinates: Coordinates
    ): PrayerTimesLoad.Loaded {
        val window = calculatePrayerWindowUseCase(result.timings, dateKey, coordinates)
        return PrayerTimesLoad.Loaded(
            timings = result.timings,
            prayerDate = result.date,
            status = calculatePrayerStatusUseCase(result.timings, date, window)
        )
    }

    // Being offline explains any failure better than the raw error does.
    private fun failureWhileOnlineOr(error: PrayerTimesError): PrayerTimesError =
        if (connectivityChecker.isConnected()) error else PrayerTimesError.Offline
}