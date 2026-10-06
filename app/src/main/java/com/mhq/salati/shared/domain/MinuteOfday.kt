package com.mhq.salati.shared.domain

import java.time.LocalTime

const val MINUTES_PER_DAY = 24 * 60

fun LocalTime.toMinuteOfDay(): Int = hour * 60 + minute