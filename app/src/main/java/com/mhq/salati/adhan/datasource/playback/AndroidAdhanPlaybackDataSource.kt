package com.mhq.salati.adhan.datasource.playback

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

class AndroidAdhanPlaybackDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("adhan_playback_service") private val service: ComponentTarget
) : AdhanPlaybackDataSource {

    private val _status = MutableStateFlow<PlaybackStatus>(PlaybackStatus.Idle)
    override val status: StateFlow<PlaybackStatus> = _status.asStateFlow()

    override fun start(prayerKey: String, isMinorTiming: Boolean, isMuted: Boolean, soundKey: String) {
        val intent = Intent(context, service.componentClass).apply {
            action = ACTION_START
            putExtra(EXTRA_PRAYER_NAME, prayerKey)
            putExtra(EXTRA_IS_MINOR_TIMING, isMinorTiming)
            putExtra(EXTRA_IS_MUTED, isMuted)
            putExtra(EXTRA_ADHAN_SOUND, soundKey)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun stop() {
        val intent = Intent(context, service.componentClass).apply {
            action = ACTION_STOP
        }
        context.startService(intent)
    }

    override fun updateStatus(status: PlaybackStatus) {
        _status.value = status
    }

    private companion object {
        // These values are read by AdhanPlaybackService and must stay identical to its own constants.
        const val ACTION_START = "com.mhq.salati.action.START_ADHAN"
        const val ACTION_STOP = "com.mhq.salati.action.STOP_ADHAN"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
        const val EXTRA_ADHAN_SOUND = "extra_adhan_sound"
    }
}