package com.mhq.salati.adhan.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mhq.PrayerAlarmNames
import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager =
        context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: PrayerAlarm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            && !alarmManager.canScheduleExactAlarms()
        ) {
            return
        }

        val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
            putExtra(PrayerAlarmReceiver.EXTRA_PRAYER_NAME, alarm.prayerName)
            putExtra(PrayerAlarmReceiver.EXTRA_IS_MINOR_TIMING, alarm.isMinorTiming)   // ADDED
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.prayerName.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.triggerAtMillis,
            pendingIntent
        )
    }

    override fun cancel(prayerName: String) {
        val intent = Intent(
            context,
            PrayerAlarmReceiver::class.java
        )
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            prayerName.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    override fun cancelAll() {
        PrayerAlarmNames.ALL.forEach { cancel(it) }   // CHANGED — was hardcoded list
    }
}