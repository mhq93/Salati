package com.mhq.salati.home.domain.usecases

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
import com.mhq.salati.testutil.FakePrayerTimesRepository
import com.mhq.salati.testutil.FakeSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class ResolvePrayerWindowForDisplayUseCaseTest {

    private val coordinates = Coordinates(30.0444, 31.2357)
    private val today = LocalDate.of(2026, 11, 11)

    private fun timings(fajr: LocalTime, dhuhr: LocalTime, asr: LocalTime, maghrib: LocalTime, isha: LocalTime) =
        PrayerTimings(
            imsak = fajr.minusMinutes(10),
            fajr = fajr,
            sunrise = fajr.plusHours(1),
            dhuhr = dhuhr,
            asr = asr,
            sunset = maghrib.minusMinutes(1),
            maghrib = maghrib,
            isha = isha,
            midnight = isha.plusHours(3),
            firstThird = isha.plusHours(2),
            lastThird = isha.plusHours(4)
        )

    private fun prayerDate(date: LocalDate) = PrayerDate(
        gregorianDate = date,
        hijriDate = HijriDate(25, HijriMonth.JUMADA_AL_AWWAL, 1448)
    )

    private fun result(date: LocalDate, dayTimings: PrayerTimings) =
        PrayerTimesResult(timings = dayTimings, date = prayerDate(date))

    /** Standard day: Fajr 05:00, Dhuhr 12:00, Asr 15:00, Maghrib 18:00, Isha 19:30. */
    private fun standardTimings() =
        timings(LocalTime.of(5, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0), LocalTime.of(19, 30))

    private fun useCase(
        cached: Map<String, PrayerTimesResult> = emptyMap(),
        network: Map<String, Result<PrayerTimesResult>> = emptyMap()
    ): ResolvePrayerWindowForDisplayUseCase {
        val clock = FakeClock(today)
        val repo = FakePrayerTimesRepository(cached, network)
        val settingsRepo = FakeSettingsRepository()
        val cachedUseCase = GetCachedPrayerTimesUseCase(repo, settingsRepo)
        val networkUseCase = GetPrayerTimesUseCase(repo, settingsRepo)
        return ResolvePrayerWindowForDisplayUseCase(
            clock = clock,
            getCachedPrayerTimesUseCase = cachedUseCase,
            getPrayerTimesUseCase = networkUseCase,
            calculatePrayerWindowUseCase = CalculatePrayerWindowUseCase(clock, networkUseCase, cachedUseCase)
        )
    }

    @Test
    fun `browsing today uses the browsed timings and never touches cache or network`() = runTest {
        val browsed = standardTimings()
        val useCase = useCase(
            cached = mapOf(today.toDateKey() to result(today, timings(LocalTime.MIDNIGHT, LocalTime.NOON, LocalTime.NOON, LocalTime.NOON, LocalTime.of(23, 0)))),
            network = mapOf(today.toDateKey() to Result.success(result(today, timings(LocalTime.MIDNIGHT, LocalTime.NOON, LocalTime.NOON, LocalTime.NOON, LocalTime.of(23, 30)))))
        )

        val window = useCase(browsed, today, coordinates)

        // Clock is fixed at 12:00, so the window runs Dhuhr 12:00 -> Asr 15:00 of the browsed day.
        assertEquals(PrayerName.ASR, window.nextPrayer)
        val zone = ZoneId.of("UTC")
        assertEquals(today.atTime(12, 0).atZone(zone).toInstant(), window.start)
        assertEquals(today.atTime(15, 0).atZone(zone).toInstant(), window.end)
    }

    @Test
    fun `browsing another day falls back to today's cached timings`() = runTest {
        val browsedDate = today.minusDays(3)
        val browsed = timings(LocalTime.of(4, 0), LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), LocalTime.of(18, 30))
        val todayTimings = standardTimings()
        val useCase = useCase(cached = mapOf(today.toDateKey() to result(today, todayTimings)))

        val window = useCase(browsed, browsedDate, coordinates)

        // The window must come from today's cached times, not the browsed ones.
        assertEquals(PrayerName.ASR, window.nextPrayer)
        assertEquals(today.atTime(12, 0).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.atTime(15, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }

    @Test
    fun `browsing another day fetches today from the network on a cache miss`() = runTest {
        val browsedDate = today.minusDays(3)
        val browsed = timings(LocalTime.of(4, 0), LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), LocalTime.of(18, 30))
        val todayTimings = standardTimings()
        val useCase = useCase(network = mapOf(today.toDateKey() to Result.success(result(today, todayTimings))))

        val window = useCase(browsed, browsedDate, coordinates)

        assertEquals(PrayerName.ASR, window.nextPrayer)
        assertEquals(today.atTime(12, 0).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.atTime(15, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }

    @Test
    fun `browsing another day uses the browsed timings as the last resort`() = runTest {
        val browsedDate = today.minusDays(3)
        val browsed = timings(LocalTime.of(4, 0), LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(17, 0), LocalTime.of(18, 30))
        val useCase = useCase(
            network = mapOf(today.toDateKey() to Result.failure(IllegalStateException("offline")))
        )

        val window = useCase(browsed, browsedDate, coordinates)

        // Fallback timings place 12:00 between Dhuhr 11:00 and Asr 14:00.
        assertEquals(PrayerName.ASR, window.nextPrayer)
        assertEquals(today.atTime(11, 0).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.atTime(14, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }

    @Test
    fun `after Isha the window runs into tomorrow's Fajr`() = runTest {
        val browsed = timings(LocalTime.of(5, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(18, 0), LocalTime.of(19, 30))
        val repo = FakePrayerTimesRepository()
        val settingsRepo = FakeSettingsRepository()
        val cachedUseCase = GetCachedPrayerTimesUseCase(repo, settingsRepo)
        val networkUseCase = GetPrayerTimesUseCase(repo, settingsRepo)
        val lateClock = FakeClock(today, LocalTime.of(23, 0))
        val useCase = ResolvePrayerWindowForDisplayUseCase(
            clock = lateClock,
            getCachedPrayerTimesUseCase = cachedUseCase,
            getPrayerTimesUseCase = networkUseCase,
            calculatePrayerWindowUseCase = CalculatePrayerWindowUseCase(lateClock, networkUseCase, cachedUseCase)
        )
        val window = useCase(browsed, today, coordinates)

        assertEquals(PrayerName.FAJR, window.nextPrayer)
        assertEquals(today.atTime(19, 30).atZone(ZoneId.of("UTC")).toInstant(), window.start)
        assertEquals(today.plusDays(1).atTime(5, 0).atZone(ZoneId.of("UTC")).toInstant(), window.end)
    }
}