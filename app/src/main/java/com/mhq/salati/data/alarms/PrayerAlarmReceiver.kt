package com.mhq.salati.data.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mhq.salati.domain.usecases.alarms.StartAdhanPlaybackUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrayerAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var startAdhanPlaybackUseCase: StartAdhanPlaybackUseCase

    companion object {
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: return
        startAdhanPlaybackUseCase(prayerName)
    }
}