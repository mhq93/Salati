package com.mhq.salati.adhan.data

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.repo.AdhanPlaybackController
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AdhanPlaybackControllerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    stateHolder: AdhanPlaybackStateHolder
) : AdhanPlaybackController {

    override val playbackState: StateFlow<AdhanPlaybackState> = stateHolder.state

    override fun start(prayerName: String, isMinorTiming: Boolean, isMuted: Boolean) {
        val intent = Intent(context, AdhanPlaybackService::class.java).apply {
            action = AdhanPlaybackService.ACTION_START
            putExtra(AdhanPlaybackService.EXTRA_PRAYER_NAME, prayerName)
            putExtra(AdhanPlaybackService.EXTRA_IS_MINOR_TIMING, isMinorTiming)
            putExtra(AdhanPlaybackService.EXTRA_IS_MUTED, isMuted)   // ADDED
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun stop() {
        val intent = Intent(
            context,
            AdhanPlaybackService::class.java
        ).apply {
            action = AdhanPlaybackService.ACTION_STOP
        }
        context.startService(intent)
    }
}