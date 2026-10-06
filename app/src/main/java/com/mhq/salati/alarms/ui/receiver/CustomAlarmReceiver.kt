package com.mhq.salati.alarms.ui.receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.mhq.salati.alarms.ui.notification.CustomAlarmNotifier
import com.mhq.salati.alarms.domain.model.OffsetDirection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CustomAlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var customAlarmNotifier: CustomAlarmNotifier

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getLongExtra(CustomAlarmIntentKeys.EXTRA_ALARM_ID, -1L)
        val label = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_LABEL).orEmpty()
        val prayerName = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_PRAYER_NAME).orEmpty()
        val offsetMinutes = intent.getIntExtra(CustomAlarmIntentKeys.EXTRA_OFFSET_MINUTES, 0) // NEW
        val offsetDirection = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_OFFSET_DIRECTION) // NEW
            ?.let { runCatching { OffsetDirection.valueOf(it) }.getOrNull() }

        // CHANGED — show() is now suspend (reads the sound setting), so this
        // needs a coroutine; goAsync() keeps the receiver alive until it's done.
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                customAlarmNotifier.show(alarmId, label, prayerName, offsetMinutes, offsetDirection)
            } finally {
                pendingResult.finish()
            }
        }
    }
}