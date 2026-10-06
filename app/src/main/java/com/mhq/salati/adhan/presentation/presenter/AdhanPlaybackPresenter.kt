package com.mhq.salati.adhan.presentation.presenter

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.usecases.UpdateAdhanPlaybackStateUseCase
import javax.inject.Inject

/** What the playback service reports about itself. */
class AdhanPlaybackPresenter @Inject constructor(
    private val updateAdhanPlaybackStateUseCase: UpdateAdhanPlaybackStateUseCase
) {
    fun onPlaybackStarted(prayerName: String) {
        updateAdhanPlaybackStateUseCase(AdhanPlaybackState.Playing(prayerName))
    }

    fun onPlaybackStopped() {
        updateAdhanPlaybackStateUseCase(AdhanPlaybackState.Idle)
    }
}