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
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.alarms.presentation.presenter.CustomAlarmNotificationPresenter
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.ui.rawRes
import com.mhq.salati.shared.ui.labelRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// CHANGED — no longer a fixed constant; the channel ID is now derived per
// sound choice, since a notification channel's sound is locked forever once
// created. Switching sounds in Settings must produce a *new* channel rather
// than trying to mutate the old one.
private const val CHANNEL_ID_PREFIX = "custom_alarms_channel"

class CustomAlarmNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
    private val presenter: CustomAlarmNotificationPresenter
) {
    @SuppressLint("StringFormatInvalid")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend fun show(
        alarmId: Long,
        label: String,
        prayerKey: String,
        offsetMinutes: Int,
        offsetDirectionKey: String?
    ) {
        val sound = presenter.customAlarmSound()
        val channelId = channelIdFor(sound)
        ensureChannelExists(channelId, sound)

        val localizedPrayerName =
            presenter.prayerNameFor(prayerKey)?.let { context.getString(it.labelRes) }
                ?: prayerKey

        val title = label.ifBlank {
            context.getString(
                R.string.custom_alarm_default_title,
                localizedPrayerName
            )
        }

        // NEW — "5 minutes before Fajr" instead of a generic body, reusing the
        // same plural strings CustomAlarmCard already uses for the list row.
        val body = buildOffsetBody(
            localizedPrayerName,
            offsetMinutes,
            presenter.offsetDirectionFor(offsetDirectionKey)
        )

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
        offsetDirection: OffsetDirectionUi?
    ): String = when {
        offsetMinutes == 0 || offsetDirection == null ->
            context.getString(R.string.at_prayer_time, localizedPrayerName)

        offsetDirection == OffsetDirectionUi.BEFORE ->
            context.resources.getQuantityString(
                R.plurals.minutes_before_prayer, offsetMinutes, offsetMinutes, localizedPrayerName
            )

        else ->
            context.resources.getQuantityString(
                R.plurals.minutes_after_prayer, offsetMinutes, offsetMinutes, localizedPrayerName
            )
    }

    private fun channelIdFor(sound: CustomAlarmSoundUi): String =
        "${CHANNEL_ID_PREFIX}_${sound.name.lowercase()}"

    // NEW — replaces the old init-block createChannelIfNeeded(); now takes the
    // resolved sound so it can pick the right channel ID and Uri, and only
    // creates it once per sound (existing channels are left alone).
    private fun ensureChannelExists(channelId: String, sound: CustomAlarmSoundUi) {
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