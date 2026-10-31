package com.mhq.salati.alarms.ui.receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.mhq.salati.adhan.presentation.presenter.AlarmReschedulePresenter
import com.mhq.salati.alarms.ui.notification.CustomAlarmNotifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CustomAlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var customAlarmNotifier: CustomAlarmNotifier
    @Inject lateinit var alarmReschedulePresenter: AlarmReschedulePresenter

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getLongExtra(CustomAlarmIntentKeys.EXTRA_ALARM_ID, -1L)
        val label = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_LABEL).orEmpty()
        val prayerName = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_PRAYER_NAME).orEmpty()
        val offsetMinutes = intent.getIntExtra(CustomAlarmIntentKeys.EXTRA_OFFSET_MINUTES, 0)
        val offsetDirectionKey = intent.getStringExtra(CustomAlarmIntentKeys.EXTRA_OFFSET_DIRECTION)

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                customAlarmNotifier.show(alarmId, label, prayerName, offsetMinutes, offsetDirectionKey)
                // Arm this alarm's next occurrence ("every day" alarms no longer stop after one ring).
                alarmReschedulePresenter.onRescheduleNeeded()
            } finally {
                pendingResult.finish()
            }
        }
    }
}