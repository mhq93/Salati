package com.mhq.salati.shared.domain

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Injectable source of the current date and time.
 *
 * Wraps [java.time] clock reads so domain code never touches the system clock directly.
 * Production binds it to the real system time; tests bind it to a fixed or scripted time,
 * which makes time-dependent use cases deterministic.
 */
interface Clock {
    /** The current date and time in the device's time zone. */
    fun now(): LocalDateTime

    /** The current calendar date in the device's time zone. */
    fun today(): LocalDate

    /** The current moment as a zone-independent [Instant]. */
    fun instant(): Instant

    /** The time zone all other methods resolve their results in. */
    fun zone(): ZoneId
}