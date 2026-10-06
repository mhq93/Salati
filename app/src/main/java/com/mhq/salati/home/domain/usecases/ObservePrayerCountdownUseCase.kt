package com.mhq.salati.home.domain.usecases

import com.mhq.salati.home.domain.model.PrayerCountdown
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.shared.domain.Clock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class ObservePrayerCountdownUseCase @Inject constructor(
    private val clock: Clock
) {
    /** Emits once a second until [window] ends; the flow completes right after the zero emission. */
    operator fun invoke(window: PrayerWindow): Flow<PrayerCountdown> = flow {
        while (true) {
            val now = clock.instant()
            val remaining = window.remainingAt(now)
            emit(PrayerCountdown(remaining = remaining, progress = window.progressAt(now)))
            if (remaining.isZero) return@flow
            delay(1_000.milliseconds)
        }
    }
}