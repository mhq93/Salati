/*
package com.mhq.salati.alarms.data.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mhq.salati.R
import com.mhq.salati.shared.domain.PrayerName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val CHANNEL_ID = "custom_alarms_channel"

class CustomAlarmNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    init { createChannelIfNeeded() }

    @SuppressLint("StringFormatInvalid")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun show(alarmId: Long, label: String, prayerName: String) {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val localizedPrayerName = PrayerName.fromStorageKey(prayerName)?.let { context.getString(it.labelRes) }
            ?: prayerName

        val title = label.ifBlank { context.getString(R.string.custom_alarm_default_title, localizedPrayerName) }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(context.getString(R.string.custom_alarm_body, localizedPrayerName))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setSound(soundUri)
            .build()

        NotificationManagerCompat.from(context).notify(alarmId.toInt(), notification)
    }

    private fun createChannelIfNeeded() {
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        if (manager.getNotificationChannel(CHANNEL_ID) != null) return

        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.custom_alarms_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), attrs)
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
    }
}
* */


package com.mhq.salati.alarms.ui.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.mhq.salati.R
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.ui.rawRes
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.ui.labelRes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// CHANGED — no longer a fixed constant; the channel ID is now derived per
// sound choice, since a notification channel's sound is locked forever once
// created. Switching sounds in Settings must produce a *new* channel rather
// than trying to mutate the old one.
private const val CHANNEL_ID_PREFIX = "custom_alarms_channel"

class CustomAlarmNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
    private val observeSettingsUseCase: ObserveSettingsUseCase
) {
    @SuppressLint("StringFormatInvalid")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend fun show(
        alarmId: Long,
        label: String,
        prayerName: String,
        offsetMinutes: Int,
        offsetDirection: OffsetDirection?
    ) {
        val sound = observeSettingsUseCase().first().customAlarmSound
        val channelId = channelIdFor(sound)
        ensureChannelExists(channelId, sound)

        val localizedPrayerName =
            PrayerName.fromStorageKey(prayerName)?.let { context.getString(it.labelRes) }
                ?: prayerName

        val title = label.ifBlank {
            context.getString(
                R.string.custom_alarm_default_title,
                localizedPrayerName
            )
        }

        // NEW — "5 minutes before Fajr" instead of a generic body, reusing the
        // same plural strings CustomAlarmCard already uses for the list row.
        val body = buildOffsetBody(localizedPrayerName, offsetMinutes, offsetDirection)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build() // no .setSound() — channel owns the sound on O+, and this
        // app's minSdk already requires channels, so no pre-O path needed

        NotificationManagerCompat.from(context).notify(alarmId.toInt(), notification)
    }

    private fun buildOffsetBody(
        localizedPrayerName: String,
        offsetMinutes: Int,
        offsetDirection: OffsetDirection?
    ): String = when {
        offsetMinutes == 0 || offsetDirection == null ->
            context.getString(R.string.at_prayer_time, localizedPrayerName)

        offsetDirection == OffsetDirection.BEFORE ->
            context.resources.getQuantityString(
                R.plurals.minutes_before_prayer, offsetMinutes, offsetMinutes, localizedPrayerName
            )

        else ->
            context.resources.getQuantityString(
                R.plurals.minutes_after_prayer, offsetMinutes, offsetMinutes, localizedPrayerName
            )
    }

    private fun channelIdFor(sound: CustomAlarmSound): String =
        "${CHANNEL_ID_PREFIX}_${sound.name.lowercase()}"

    // NEW — replaces the old init-block createChannelIfNeeded(); now takes the
    // resolved sound so it can pick the right channel ID and Uri, and only
    // creates it once per sound (existing channels are left alone).
    private fun ensureChannelExists(channelId: String, sound: CustomAlarmSound) {
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        if (manager.getNotificationChannel(channelId) != null) return

        val soundUri = sound.rawRes?.let {
            "android.resource://${context.packageName}/$it".toUri()
        } ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val channel = NotificationChannel(
            channelId,
            context.getString(R.string.custom_alarms_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(soundUri, attrs)
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
    }
}