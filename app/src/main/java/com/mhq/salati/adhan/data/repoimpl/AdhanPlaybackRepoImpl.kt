package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.datasource.playback.AdhanPlaybackDataSource
import com.mhq.salati.adhan.datasource.playback.PlaybackStatus
import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import com.mhq.salati.settings.domain.model.AdhanSound
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdhanPlaybackRepoImpl @Inject constructor(
    private val adhanPlaybackDataSource: AdhanPlaybackDataSource
) : AdhanPlaybackController {

    override val playbackState: Flow<AdhanPlaybackState> =
        adhanPlaybackDataSource.status.map { it.toDomain() }

    override fun start(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean, adhanSound: AdhanSound) {
        adhanPlaybackDataSource.start(prayerName, isMinorTiming, isMuted, adhanSound.name)
    }

    override fun stop() {
        adhanPlaybackDataSource.stop()
    }

    override fun updatePlaybackState(state: AdhanPlaybackState) {
        adhanPlaybackDataSource.updateStatus(state.toDataSource())
    }

    private fun PlaybackStatus.toDomain(): AdhanPlaybackState = when (this) {
        is PlaybackStatus.Idle -> AdhanPlaybackState.Idle
        is PlaybackStatus.Playing -> AdhanPlaybackState.Playing(prayerKey)
    }

    private fun AdhanPlaybackState.toDataSource(): PlaybackStatus = when (this) {
        is AdhanPlaybackState.Idle -> PlaybackStatus.Idle
        is AdhanPlaybackState.Playing -> PlaybackStatus.Playing(prayerName)
    }
}