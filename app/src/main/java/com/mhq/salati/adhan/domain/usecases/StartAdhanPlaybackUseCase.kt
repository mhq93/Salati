package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import javax.inject.Inject

class StartAdhanPlaybackUseCase @Inject constructor(
    private val controller: AdhanPlaybackController
) {
    operator fun invoke(prayerName: String) = controller.start(prayerName)
}