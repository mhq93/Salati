package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Leave for now
class ObserveAdhanPlaybackStateUseCase @Inject constructor(
    private val adhanPlaybackController: AdhanPlaybackController
) {
    operator fun invoke(): Flow<AdhanPlaybackState> = adhanPlaybackController.playbackState
}