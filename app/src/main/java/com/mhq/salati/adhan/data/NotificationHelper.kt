package com.mhq.salati.adhan.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val CHANNEL_ID = "prayer_times_channel"
        private const val CHANNEL_NAME = "Prayer Times"
        private const val CHANNEL_DESCRIPTION = "Notifications for prayer times"
    }

    fun ensureChannelsCreated() {
        createNotificationChannel()
        createAdhanPlaybackChannel()
    }

    private fun createNotificationChannel() {
        val manager = context.getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = CHANNEL_DESCRIPTION
            setSound(null, null)
        }

        manager.createNotificationChannel(channel)
    }

    private fun createAdhanPlaybackChannel() {
        val channel = NotificationChannel(
            AdhanPlaybackService.CHANNEL_ID,
            "Adhan Playback",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            setSound(null, null)
            description = "Ongoing notification shown while the Adhan is playing"
        }
        context.getSystemService(
            NotificationManager::class.java
        )?.createNotificationChannel(channel)
    }
}