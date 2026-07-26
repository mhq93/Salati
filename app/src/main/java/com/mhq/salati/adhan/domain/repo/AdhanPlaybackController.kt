package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import kotlinx.coroutines.flow.StateFlow

interface AdhanPlaybackController {
    val playbackState: StateFlow<AdhanPlaybackState>
    fun start(prayerName: String)
    fun stop()
}