package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveAdhanPlaybackStateUseCase @Inject constructor(
    private val controller: AdhanPlaybackController
) {
    operator fun invoke(): StateFlow<AdhanPlaybackState> = controller.playbackState
}