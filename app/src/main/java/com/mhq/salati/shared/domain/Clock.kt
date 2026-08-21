package com.mhq.salati.shared.domain

import java.time.LocalDate
import java.time.LocalDateTime

interface Clock {
    fun now(): LocalDateTime
    fun today(): LocalDate
}