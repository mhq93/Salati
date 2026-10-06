package com.mhq.salati.shared.data

import com.mhq.salati.shared.domain.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class SystemClock @Inject constructor() : Clock {
    override fun now(): LocalDateTime = LocalDateTime.now()
    override fun today(): LocalDate = LocalDate.now()
    override fun instant(): Instant = Instant.now()
    override fun zone(): ZoneId = ZoneId.systemDefault()
}