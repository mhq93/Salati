package com.mhq.salati.adhan.datasource.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

class AndroidPrayerAlarmDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("prayer_alarm_receiver") private val receiver: ComponentTarget
) : PrayerAlarmDataSource {

    private val alarmManager =
        context.getSystemService(AlarmManager::class.java)

    override fun schedule(request: PrayerAlarmRequest) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            && !alarmManager.canScheduleExactAlarms()
        ) {
            return
        }

        val intent = Intent(context, receiver.componentClass).apply {
            putExtra(EXTRA_PRAYER_NAME, request.prayerKey)
            putExtra(EXTRA_IS_MINOR_TIMING, request.isMinorTiming)
            putExtra(EXTRA_IS_MUTED, request.isMuted)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            request.prayerKey.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            request.triggerAtMillis,
            pendingIntent
        )
    }

    override fun cancel(prayerKey: String) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            prayerKey.hashCode(),
            Intent(context, receiver.componentClass),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private companion object {
        // These keys are read by PrayerAlarmReceiver and must stay identical to its own constants.
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
    }
}