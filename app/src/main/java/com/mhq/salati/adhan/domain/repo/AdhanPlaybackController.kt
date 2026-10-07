package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.settings.domain.model.AdhanSound
import kotlinx.coroutines.flow.Flow

interface AdhanPlaybackController {
    val playbackState: Flow<AdhanPlaybackState>
    fun start(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean, adhanSound: AdhanSound)
    fun stop()

    /** Called by the playback service whenever it starts or stops playing. */
    fun updatePlaybackState(state: AdhanPlaybackState)
}