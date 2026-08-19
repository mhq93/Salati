package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import javax.inject.Inject

class StopAdhanPlaybackUseCase @Inject constructor(
    private val adhanPlaybackController: AdhanPlaybackController
) {
    operator fun invoke() = adhanPlaybackController.stop()
}