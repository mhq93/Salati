package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.prayertimes.domain.model.HijriDate
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.HijriMonth
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import com.mhq.salati.testutil.FakeClock
import com.mhq.salati.testutil.FakeConnectivityChecker
import com.mhq.salati.testutil.FakePrayerTimesRepository
import com.mhq.salati.testutil.FakeSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class LoadPrayerTimesUseCaseTest {

    private val coordinates = Coordinates(30.0444, 31.2357)
    private val date = LocalDate.of(2026, 11, 11)

    private fun timings() = PrayerTimings(
        imsak = LocalTime.of(4, 50),
        fajr = LocalTime.of(5, 0),
        sunrise = LocalTime.of(6, 10),
        dhuhr = LocalTime.of(12, 0),
        asr = LocalTime.of(15, 0),
        sunset = LocalTime.of(17, 55),
        maghrib = LocalTime.of(18, 0),
        isha = LocalTime.of(19, 30),
        midnight = LocalTime.of(22, 30),
        firstThird = LocalTime.of(21, 30),
        lastThird = LocalTime.of(23, 30)
    )

    private fun result() = PrayerTimesResult(
        timings = timings(),
        date = PrayerDate(date, HijriDate(25, HijriMonth.JUMADA_AL_AWWAL, 1448))
    )

    private fun useCase(
        cached: Map<String, PrayerTimesResult> = emptyMap(),
        network: Map<String, Result<PrayerTimesResult>> = emptyMap(),
        online: Boolean = true,
        networkDelayMillis: Long = 0
    ): LoadPrayerTimesUseCase {
        val clock = FakeClock(date)
        val repo = FakePrayerTimesRepository(cached, network, networkDelayMillis)
        val settingsRepo = FakeSettingsRepository()
        val cachedUseCase = GetCachedPrayerTimesUseCase(repo, settingsRepo)
        val networkUseCase = GetPrayerTimesUseCase(repo, settingsRepo)
        return LoadPrayerTimesUseCase(
            getCachedPrayerTimesUseCase = cachedUseCase,
            getPrayerTimesUseCase = networkUseCase,
            resolvePrayerWindowForDisplayUseCase = ResolvePrayerWindowForDisplayUseCase(
                clock = clock,
                getCachedPrayerTimesUseCase = cachedUseCase,
                getPrayerTimesUseCase = networkUseCase,
                calculatePrayerWindowUseCase = CalculatePrayerWindowUseCase(clock, networkUseCase, cachedUseCase)
            ),
            calculatePrayerStatusUseCase = CalculatePrayerStatusUseCase(
                clock = clock,
                calculateCurrentPrayerNameUseCase = CalculateCurrentPrayerNameUseCase(clock),
                calculatePastPrayersUseCase = CalculatePastPrayersUseCase(clock)
            ),
            connectivityChecker = FakeConnectivityChecker(online)
        )
    }

    @Test
    fun `cache hit emits a single Loaded without going online`() = runTest {
        val useCase = useCase(cached = mapOf(date.toDateKey() to result()))

        val emissions = useCase(date, coordinates).toList()

        assertEquals(1, emissions.size)
        val loaded = emissions[0] as PrayerTimesLoad.Loaded
        assertEquals(LocalTime.of(5, 0), loaded.timings.fajr)
        assertEquals(date, loaded.prayerDate.gregorianDate)
    }

    @Test
    fun `cache miss emits FetchingFromNetwork then Loaded`() = runTest {
        val useCase = useCase(network = mapOf(date.toDateKey() to Result.success(result())))

        val emissions = useCase(date, coordinates).toList()

        assertEquals(2, emissions.size)
        assertEquals(PrayerTimesLoad.FetchingFromNetwork, emissions[0])
        val loaded = emissions[1] as PrayerTimesLoad.Loaded
        assertEquals(LocalTime.of(12, 0), loaded.timings.dhuhr)
    }

    @Test
    fun `today's Loaded status carries the current prayer and past prayers`() = runTest {
        val useCase = useCase(cached = mapOf(date.toDateKey() to result()))

        val loaded = useCase(date, coordinates).first() as PrayerTimesLoad.Loaded

        // Clock fixed at 12:00: Dhuhr is the current prayer; Fajr and sunrise have passed.
        assertEquals(PrayerName.DHUHR, loaded.status.currentPrayer)
        assertTrue(loaded.status.pastPrayers.contains(PrayerName.FAJR))
        assertTrue(loaded.status.pastPrayers.contains(PrayerName.DHUHR))
    }

    @Test
    fun `a non-today Loaded status carries no current prayer or past prayers`() = runTest {
        val otherDate = date.minusDays(1)
        val useCase = useCase(cached = mapOf(otherDate.toDateKey() to result()))

        val loaded = useCase(otherDate, coordinates).first() as PrayerTimesLoad.Loaded

        assertEquals(null, loaded.status.currentPrayer)
        assertEquals(emptySet<PrayerName>(), loaded.status.pastPrayers)
    }

    @Test
    fun `network failure while offline maps to PrayerTimesError Offline`() = runTest {
        val useCase = useCase(
            network = mapOf(date.toDateKey() to Result.failure(IllegalStateException("boom"))),
            online = false
        )

        val emissions = useCase(date, coordinates).toList()

        assertEquals(2, emissions.size)
        val failed = emissions[1] as PrayerTimesLoad.Failed
        assertEquals(PrayerTimesError.Offline, failed.error)
    }

    @Test
    fun `network failure while online maps to PrayerTimesError Other with the message`() = runTest {
        val useCase = useCase(
            network = mapOf(date.toDateKey() to Result.failure(IllegalStateException("http 500"))),
            online = true
        )

        val emissions = useCase(date, coordinates).toList()

        val failed = emissions[1] as PrayerTimesLoad.Failed
        assertEquals(PrayerTimesError.Other("http 500"), failed.error)
    }

    @Test
    fun `a network fetch slower than the timeout maps to PrayerTimesError TimedOut`() = runTest {
        val useCase = useCase(
            network = mapOf(date.toDateKey() to Result.success(result())),
            online = true,
            // The fetch timeout is 5s; runTest's virtual clock makes this instantaneous.
            networkDelayMillis = 10_000
        )

        val emissions = useCase(date, coordinates).toList()

        assertEquals(2, emissions.size)
        val failed = emissions[1] as PrayerTimesLoad.Failed
        assertEquals(PrayerTimesError.TimedOut, failed.error)
    }
}