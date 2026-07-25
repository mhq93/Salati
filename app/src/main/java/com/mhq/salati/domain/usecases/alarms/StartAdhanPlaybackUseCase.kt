package com.mhq.salati.domain.usecases.alarms

import com.mhq.salati.domain.repo.alarms.AdhanPlaybackController
import javax.inject.Inject

class StartAdhanPlaybackUseCase @Inject constructor(
    private val controller: AdhanPlaybackController
) {
    operator fun invoke(prayerName: String) = controller.start(prayerName)
}