package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import javax.inject.Inject

class UpdateAdhanPlaybackStateUseCase @Inject constructor(
    private val adhanPlaybackController: AdhanPlaybackController
) {
    operator fun invoke(adhanPlaybackState: AdhanPlaybackState) =
        adhanPlaybackController.updatePlaybackState(adhanPlaybackState)
}