package com.mhq.salati.adhan.presentation.presenter

import com.mhq.salati.adhan.domain.usecases.StartAdhanPlaybackUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/** What the prayer alarm receiver asks for when an alarm goes off. */
class PrayerAlarmPresenter @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val startAdhanPlaybackUseCase: StartAdhanPlaybackUseCase
) {
    suspend fun onPrayerAlarm(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean) {
        val notificationsEnabled = observeSettingsUseCase().first().notificationsEnabled
        if (notificationsEnabled) {
            startAdhanPlaybackUseCase(prayerName, isMinorTiming, isMuted)
        }
    }
}