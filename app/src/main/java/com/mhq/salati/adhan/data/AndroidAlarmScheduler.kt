package com.mhq.salati.adhan.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
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
            Log.w(
                "AlarmScheduler",
                "Cannot schedule ${alarm.prayerName}: exact alarm permission denied"
            )
            return
        }

        val intent = Intent(
            context,
            PrayerAlarmReceiver::class.java
        ).apply {
            putExtra(
                PrayerAlarmReceiver.EXTRA_PRAYER_NAME,
                alarm.prayerName
            )
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

        Log.d(
            "AlarmScheduler",
            "Scheduling $alarm at ${alarm.triggerAtMillis}, " +
                    "canScheduleExactAlarms=${alarmManager.canScheduleExactAlarms()}"
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
        listOf(
            "Fajr",
            "Dhuhr",
            "Asr",
            "Maghrib",
            "Isha",
            "Imsak",
            "Shorouq",
            "First Third",
            "Midnight",
            "Last Third"
        )
            .forEach { cancel(it) }
    }
}