package com.mhq.salati.home.domain.model

import java.time.Duration

/**
 * A snapshot of the countdown to the next prayer at one moment.
 *
 * @property remaining time left until the window ends; zero once it has ended
 * @property progress how far through the window we are, from 0f at its start to 1f at its end
 */
data class PrayerCountdown(
    val remaining: Duration,
    val progress: Float
)