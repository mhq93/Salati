package com.mhq.salati.testutil

import com.mhq.salati.shared.domain.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class FakeClock(
    private val fixedToday: LocalDate,
    private val timeOfDay: LocalTime = LocalTime.NOON,
    private val zoneId: ZoneId = ZoneId.of("UTC")
) : Clock {
    override fun now(): LocalDateTime = fixedToday.atTime(timeOfDay)
    override fun today(): LocalDate = fixedToday
    override fun instant(): Instant = now().atZone(zoneId).toInstant()
    override fun zone(): ZoneId = zoneId
}