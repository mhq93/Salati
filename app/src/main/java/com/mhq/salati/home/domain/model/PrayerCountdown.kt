package com.mhq.salati.home.domain.model

import java.time.Duration

data class PrayerCountdown(
    val remaining: Duration,
    val progress: Float
)