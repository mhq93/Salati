package com.mhq.salati.alarms.data.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mhq.salati.alarms.data.notification.CustomAlarmNotifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomAlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var notifier: CustomAlarmNotifier

    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getLongExtra(CustomAlarmIntentKeys.EXTRA_ALARM_ID, -1L)
        val label = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_LABEL).orEmpty()
        val prayerName = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_PRAYER_NAME).orEmpty()
        notifier.show(alarmId, label, prayerName)
    }
}