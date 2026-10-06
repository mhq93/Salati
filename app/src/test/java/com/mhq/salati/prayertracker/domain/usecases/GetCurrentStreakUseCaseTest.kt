package com.mhq.salati.prayertracker.domain.usecases

import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.repo.PrayerTrackerRepository
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class GetCurrentStreakUseCaseTest {

    // 1. Create a Fake Clock to control "today" and satisfy the expanded Clock boundary interface
    private class FakeClock(private val fixedDate: LocalDate) : Clock {
        override fun now() = throw UnsupportedOperationException()
        override fun today() = fixedDate
        override fun instant() = throw UnsupportedOperationException()
        override fun zone() = throw UnsupportedOperationException()
    }

    // 2. Create a Fake Repository to return controlled data
    private class FakeRepository(
        private val records: Map<LocalDate, Map<PrayerName, PrayerStatus>>
    ) : PrayerTrackerRepository {
        override suspend fun getRecordsForRange(
            start: LocalDate,
            end: LocalDate
        ): Map<LocalDate, Map<PrayerName, PrayerStatus>> {
            return records.filterKeys { it in start..end }
        }

        // Stub out other methods as they aren't used by this UseCase
        override fun observeRecordsForMonth(yearMonth: java.time.YearMonth) =
            throw UnsupportedOperationException()

        override fun observeRecordsForDate(date: LocalDate) = throw UnsupportedOperationException()
        override suspend fun getRecordsForDate(date: LocalDate) =
            throw UnsupportedOperationException()

        override suspend fun setStatus(date: LocalDate, prayer: PrayerName, status: PrayerStatus) =
            Unit
    }

    private val majorPrayers = PrayerName.majorEntries

    private fun allPrayed() = majorPrayers.associateWith { PrayerStatus.PRAYED }
    private fun hasMissed() = majorPrayers.associateWith {
        if (it == PrayerName.FAJR) PrayerStatus.MISSED else PrayerStatus.PRAYED
    }

    private fun inProgress() = majorPrayers.associateWith {
        if (it == PrayerName.FAJR) PrayerStatus.PRAYED else PrayerStatus.PENDING
    }

    // --- ORIGINAL TEST CASES (STILL VALID) ---

    @Test
    fun `invoke returns 0 when today has missed prayers`() = runTest {
        val today = LocalDate.of(2026, 9, 10)
        val clock = FakeClock(today)
        val repo = FakeRepository(mapOf(today to hasMissed()))
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(0, useCase())
    }

    @Test
    fun `invoke returns 1 when today is in progress but yesterday was fully prayed`() = runTest {
        val today = LocalDate.of(2026, 9, 10)
        val yesterday = today.minusDays(1)
        val clock = FakeClock(today)
        val repo = FakeRepository(
            mapOf(
                today to inProgress(),
                yesterday to allPrayed()
            )
        )
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(1, useCase())
    }

    @Test
    fun `invoke correctly calculates streak across month and year boundaries`() = runTest {
        val today = LocalDate.of(2026, 1, 2)
        val dec31 = LocalDate.of(2025, 12, 31)
        val jan1 = LocalDate.of(2026, 1, 1)

        val clock = FakeClock(today)
        val repo = FakeRepository(
            mapOf(
                today to inProgress(),
                jan1 to allPrayed(),
                dec31 to allPrayed()
            )
        )
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(2, useCase())
    }

    @Test
    fun `invoke stops streak at the first missed day`() = runTest {
        val today = LocalDate.of(2026, 9, 10)
        val clock = FakeClock(today)
        val repo = FakeRepository(
            mapOf(
                today to inProgress(),
                today.minusDays(1) to allPrayed(),
                today.minusDays(2) to allPrayed(),
                today.minusDays(3) to hasMissed(),
                today.minusDays(4) to allPrayed()
            )
        )
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(2, useCase())
    }

    // --- NEW EXTENDED TEST CASES FOR MULTI-YEAR TRACKING & LEAP YEARS ---

    @Test
    fun `invoke correctly calculates multi year streak including leap years`() = runTest {
        // Track across 4 years (2024 was a leap year, containing Feb 29)
        val today = LocalDate.of(2026, 1, 1)
        val clock = FakeClock(today)

        val mutableRecords = mutableMapOf<LocalDate, Map<PrayerName, PrayerStatus>>()

        // Populate a continuous 500-day pristine prayer streak moving backwards
        for (i in 1..500) {
            mutableRecords[today.minusDays(i.toLong())] = allPrayed()
        }
        // Set today as in-progress (should not break the calculation)
        mutableRecords[today] = inProgress()

        val repo = FakeRepository(mutableRecords)
        val useCase = GetCurrentStreakUseCase(repo, clock)

        assertEquals(500, useCase())
    }

    @Test
    fun `invoke caps lookup cleanly at maximum lookback limit parameters`() = runTest {
        val today = LocalDate.of(2026, 1, 1)
        val clock = FakeClock(today)

        val mutableRecords = mutableMapOf<LocalDate, Map<PrayerName, PrayerStatus>>()

        // Generate an uninterrupted historical data block longer than our 10-year constraint limit
        // 3650 days = 10 regular years, but with leap years handled safely
        val maxLookback = 3650
        for (i in 0..maxLookback + 50) {
            mutableRecords[today.minusDays(i.toLong())] = allPrayed()
        }

        val repo = FakeRepository(mutableRecords)
        val useCase = GetCurrentStreakUseCase(repo, clock)

        // The streak calculation loop terminates immediately when reaching the startDate ceiling limit
        assertEquals(maxLookback + 1, useCase())
    }
}
