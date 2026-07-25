package com.mhq.salati.data.alarms

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.mhq.salati.data.service.AdhanPlaybackService
import com.mhq.salati.data.service.AdhanPlaybackStateHolder
import com.mhq.salati.domain.model.alarms.AdhanPlaybackState
import com.mhq.salati.domain.repo.alarms.AdhanPlaybackController
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AdhanPlaybackControllerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    stateHolder: AdhanPlaybackStateHolder
) : AdhanPlaybackController {

    override val playbackState: StateFlow<AdhanPlaybackState> = stateHolder.state

    override fun start(prayerName: String) {
        val intent = Intent(
            context,
            AdhanPlaybackService::class.java
        ).apply {
            action = AdhanPlaybackService.ACTION_START
            putExtra(
                AdhanPlaybackService.EXTRA_PRAYER_NAME,
                prayerName
            )
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