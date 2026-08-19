package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import javax.inject.Inject

class StartAdhanPlaybackUseCase @Inject constructor(
    private val adhanPlaybackController: AdhanPlaybackController
) {
    operator fun invoke(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean) =
        adhanPlaybackController.start(prayerName, isMinorTiming, isMuted)
}