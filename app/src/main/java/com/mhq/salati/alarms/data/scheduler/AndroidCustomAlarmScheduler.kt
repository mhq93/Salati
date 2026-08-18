package com.mhq.salati.alarms.data.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.scheduler.CustomAlarmScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidCustomAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : CustomAlarmScheduler {

    private val alarmManager: AlarmManager? = context.getSystemService()

    override fun schedule(customAlarm: CustomAlarm, triggerAtMillis: Long) {
        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntentFor(customAlarm.id, customAlarm.label, customAlarm.prayerName)
        )
    }

    override fun cancel(alarmId: Long) {
        val pendingIntent = pendingIntentFor(alarmId, "", "")
        alarmManager?.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun pendingIntentFor(alarmId: Long, label: String, prayerName: String): PendingIntent {
        val intent = Intent(context, CustomAlarmReceiver::class.java).apply {
            putExtra(CustomAlarmIntentKeys.EXTRA_ALARM_ID, alarmId)
            putExtra(CustomAlarmIntentKeys.EXTRA_LABEL, label)
            putExtra(CustomAlarmIntentKeys.EXTRA_PRAYER_NAME, prayerName)
        }
        return PendingIntent.getBroadcast(
            context,
            alarmId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}