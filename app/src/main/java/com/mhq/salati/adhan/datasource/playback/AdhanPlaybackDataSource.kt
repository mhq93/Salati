package com.mhq.salati.adhan.datasource.playback

import kotlinx.coroutines.flow.StateFlow

interface AdhanPlaybackDataSource {
    val status: StateFlow<PlaybackStatus>

    fun start(prayerKey: String, isMinorTiming: Boolean, isMuted: Boolean)
    fun stop()
    fun updateStatus(status: PlaybackStatus)
}