package com.mhq.salati.adhan.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mhq.salati.adhan.domain.usecases.StartAdhanPlaybackUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrayerAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var startAdhanPlaybackUseCase: StartAdhanPlaybackUseCase

    companion object {
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: return
        val isMinorTiming = intent.getBooleanExtra(EXTRA_IS_MINOR_TIMING, false)
        val isMuted = intent.getBooleanExtra(EXTRA_IS_MUTED, false)
        startAdhanPlaybackUseCase(prayerName, isMinorTiming, isMuted)
    }
}