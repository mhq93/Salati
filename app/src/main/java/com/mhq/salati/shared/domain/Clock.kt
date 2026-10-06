package com.mhq.salati.shared.domain

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

interface Clock {
    fun now(): LocalDateTime
    fun today(): LocalDate
    fun instant(): Instant
    fun zone(): ZoneId
}