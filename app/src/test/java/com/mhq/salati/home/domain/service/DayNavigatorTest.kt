package com.mhq.salati.home.domain.service

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.testutil.FakeClock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class DayNavigatorTest {

    private val today = LocalDate.of(2026, 11, 11)
    private val clock = FakeClock(today)
    private val navigator = DayNavigator(clock)

    // --- isToday / today / previousOrNull / next ---

    @Test
    fun `isToday is true only for the clock's day`() {
        assertTrue(navigator.isToday(today))
        assertFalse(navigator.isToday(today.minusDays(1)))
        assertFalse(navigator.isToday(today.plusDays(1)))
    }

    @Test
    fun `today returns the clock's day`() {
        assertEquals(today, navigator.today())
    }

    @Test
    fun `previousOrNull returns null for today`() {
        assertNull(navigator.previousOrNull(today))
    }

    @Test
    fun `previousOrNull returns the previous day for any past or future day`() {
        assertEquals(today.minusDays(1), navigator.previousOrNull(today.minusDays(1)))
        assertEquals(today, navigator.previousOrNull(today.plusDays(1)))
    }

    @Test
    fun `next returns the following day`() {
        assertEquals(today.plusDays(1), navigator.next(today))
    }

    // --- rolloverDate ---

    @Test
    fun `rolloverDate returns tomorrow when the window ends after the day`() {
        val window = PrayerWindow(
            nextPrayer = PrayerName.FAJR,
            start = today.atTime(18, 0).atZone(clock.zone()).toInstant(),
            end = today.plusDays(1).atTime(5, 0).atZone(clock.zone()).toInstant()
        )
        assertEquals(today.plusDays(1), navigator.rolloverDate(today, window))
    }

    @Test
    fun `rolloverDate returns null when the window ends within the day`() {
        val window = PrayerWindow(
            nextPrayer = PrayerName.MAGHRIB,
            start = today.atTime(12, 0).atZone(clock.zone()).toInstant(),
            end = today.atTime(18, 0).atZone(clock.zone()).toInstant()
        )
        assertNull(navigator.rolloverDate(today, window))
    }

    @Test
    fun `rolloverDate returns null when there is no window`() {
        assertNull(navigator.rolloverDate(today, null))
    }

    // --- endOfMonth ---

    @Test
    fun `endOfMonth returns the month's last day for a mid-month date`() {
        assertEquals(
            LocalDate.of(2026, 11, 30),
            navigator.endOfMonth(LocalDate.of(2026, 11, 11))
        )
    }

    @Test
    fun `endOfMonth returns the same day when the date already is the month's last day`() {
        assertEquals(
            LocalDate.of(2026, 11, 30),
            navigator.endOfMonth(LocalDate.of(2026, 11, 30))
        )
    }

    @Test
    fun `endOfMonth returns the next month's last day when on the last day`() {
        assertEquals(
            LocalDate.of(2026, 12, 31),
            navigator.endOfMonth(LocalDate.of(2026, 11, 30))
        )
    }

    @Test
    fun `endOfMonth handles short months from their last day`() {
        assertEquals(
            LocalDate.of(2026, 3, 31),
            navigator.endOfMonth(LocalDate.of(2026, 2, 28))
        )
    }

    @Test
    fun `endOfMonth handles leap year February`() {
        assertEquals(
            LocalDate.of(2024, 2, 29),
            navigator.endOfMonth(LocalDate.of(2024, 2, 10))
        )
        // From Feb 29 itself: the next month's last day
        assertEquals(
            LocalDate.of(2024, 3, 31),
            navigator.endOfMonth(LocalDate.of(2024, 2, 29))
        )
    }

    @Test
    fun `endOfMonth crosses the year boundary from December 31`() {
        assertEquals(
            LocalDate.of(2027, 1, 31),
            navigator.endOfMonth(LocalDate.of(2026, 12, 31))
        )
    }
}