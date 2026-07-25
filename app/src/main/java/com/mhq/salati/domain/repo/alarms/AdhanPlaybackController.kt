package com.mhq.salati.domain.repo.alarms

import com.mhq.salati.domain.model.alarms.AdhanPlaybackState
import kotlinx.coroutines.flow.StateFlow

interface AdhanPlaybackController {
    val playbackState: StateFlow<AdhanPlaybackState>
    fun start(prayerName: String)
    fun stop()
}