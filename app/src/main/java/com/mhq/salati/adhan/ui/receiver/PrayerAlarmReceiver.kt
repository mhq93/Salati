package com.mhq.salati.adhan.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mhq.salati.adhan.presentation.presenter.PrayerAlarmPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PrayerAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var prayerAlarmPresenter: PrayerAlarmPresenter

    companion object {
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: return
        val isMinorTiming = intent.getBooleanExtra(EXTRA_IS_MINOR_TIMING, false)
        val isMuted = intent.getBooleanExtra(EXTRA_IS_MUTED, false)

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                prayerAlarmPresenter.onPrayerAlarm(prayerName, isMinorTiming, isMuted)
            } finally {
                pendingResult.finish()
            }
        }
    }
}