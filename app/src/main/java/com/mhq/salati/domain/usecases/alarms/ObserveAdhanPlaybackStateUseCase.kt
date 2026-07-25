package com.mhq.salati.domain.usecases.alarms

import com.mhq.salati.domain.model.alarms.AdhanPlaybackState
import com.mhq.salati.domain.repo.alarms.AdhanPlaybackController
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveAdhanPlaybackStateUseCase @Inject constructor(
    private val controller: AdhanPlaybackController
) {
    operator fun invoke(): StateFlow<AdhanPlaybackState> = controller.playbackState
}