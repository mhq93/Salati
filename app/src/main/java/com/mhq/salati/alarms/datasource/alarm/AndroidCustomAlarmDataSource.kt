package com.mhq.salati.alarms.datasource.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.getSystemService
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

class AndroidCustomAlarmDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("custom_alarm_receiver") private val receiver: ComponentTarget
) : CustomAlarmDataSource {

    private val alarmManager: AlarmManager? = context.getSystemService()

    override fun schedule(request: CustomAlarmRequest) {
        // Without the exact-alarm permission (Android 12+) scheduling would throw, so skip quietly.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager?.canScheduleExactAlarms() == false) {
            return
        }

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            request.triggerAtMillis,
            pendingIntentFor(
                request.alarmId,
                request.label,
                request.prayerKey,
                request.offsetMinutes,
                request.offsetDirection
            )
        )
    }

    override fun cancel(alarmId: Long) {
        val pendingIntent = pendingIntentFor(alarmId, "", "", 0, null)
        alarmManager?.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun pendingIntentFor(
        alarmId: Long,
        label: String,
        prayerKey: String,
        offsetMinutes: Int,
        offsetDirection: String?
    ): PendingIntent {
        val intent = Intent(context, receiver.componentClass).apply {
            putExtra(EXTRA_ALARM_ID, alarmId)
            putExtra(EXTRA_LABEL, label)
            putExtra(EXTRA_PRAYER_NAME, prayerKey)
            putExtra(EXTRA_OFFSET_MINUTES, offsetMinutes)
            putExtra(EXTRA_OFFSET_DIRECTION, offsetDirection)
        }
        return PendingIntent.getBroadcast(
            context,
            alarmId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private companion object {
        // These keys are read by CustomAlarmReceiver and must stay identical to its CustomAlarmIntentKeys.
        const val EXTRA_ALARM_ID = "extra_alarm_id"
        const val EXTRA_LABEL = "extra_label"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_OFFSET_MINUTES = "extra_offset_minutes"
        const val EXTRA_OFFSET_DIRECTION = "extra_offset_direction"
    }
}