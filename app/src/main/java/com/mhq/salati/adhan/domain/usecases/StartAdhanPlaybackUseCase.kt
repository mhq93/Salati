package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StartAdhanPlaybackUseCase @Inject constructor(
    private val adhanPlaybackController: AdhanPlaybackController,
    private val observeSettingsUseCase: ObserveSettingsUseCase
) {
    suspend operator fun invoke(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean) {
        val adhanSound = observeSettingsUseCase().first().adhanSound
        val isSilenced = !isMinorTiming && adhanSound == AdhanSound.SILENT

        adhanPlaybackController.start(prayerName, isMinorTiming, isMuted || isSilenced, adhanSound)
    }
}