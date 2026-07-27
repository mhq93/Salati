package com.mhq.salati.adhan.data

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.mhq.salati.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdhanPlaybackService : Service() {

    @Inject
    lateinit var stateHolder: AdhanPlaybackStateHolder

    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var focusRequest: AudioFocusRequest? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startPlayback(intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "Prayer")
            ACTION_STOP -> stopPlayback()
        }
        return START_NOT_STICKY
    }

    private fun startPlayback(prayerName: String) {
        val notification = buildNotification(prayerName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }

        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
            .setAudioAttributes(attrs)
            .build()

        val focusResult = audioManager?.requestAudioFocus(focusRequest!!)
        if (focusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stopPlayback()
            return
        }

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(attrs)
            setDataSource(this@AdhanPlaybackService, adhanUri())
            setOnPreparedListener { it.start() }
            setOnCompletionListener { stopPlayback() }
            setOnErrorListener { _, _, _ -> stopPlayback(); true }
            prepareAsync()
        }
    }

    private fun stopPlayback() {
        mediaPlayer?.apply { if (isPlaying) stop(); release() }
        mediaPlayer = null
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        stateHolder.setIdle()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun adhanUri(): Uri = "android.resource://$packageName/${R.raw.adhan}".toUri()

    private fun buildNotification(prayerName: String): Notification {
        val stopIntent =
            Intent(this, AdhanPlaybackService::class.java).apply { action = ACTION_STOP }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.adhan_playing_title))
            .setContentText(getString(R.string.adhan_playing_body, prayerName))
            .setSmallIcon(R.drawable.ic_notification)
            .setSilent(true)
            .setOngoing(true)
            .addAction(R.drawable.ic_stop, getString(R.string.stop), stopPendingIntent)
            .build()
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        super.onDestroy()
    }

    companion object {
        const val ACTION_START = "com.mhq.salati.action.START_ADHAN"
        const val ACTION_STOP = "com.mhq.salati.action.STOP_ADHAN"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        private const val NOTIFICATION_ID = 501
        const val CHANNEL_ID = "adhan_playback_channel"
    }
}

//@AndroidEntryPoint
//class AdhanPlaybackService : Service() {
//
//    @Inject
//    lateinit var stateHolder: AdhanPlaybackStateHolder
//
//    private var mediaPlayer: MediaPlayer? = null
//    private var audioManager: AudioManager? = null
//    private var focusRequest: AudioFocusRequest? = null
//
//    override fun onBind(intent: Intent?): IBinder? = null
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        when (intent?.action) {
//            ACTION_START -> startPlayback(intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "Prayer")
//            ACTION_STOP -> stopPlayback()
//        }
//        return START_NOT_STICKY
//    }
//
//        @SuppressLint("ForegroundServiceType")
//        private fun startPlayback(prayerName: String) {
//            val notification = buildNotification(prayerName)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                startForeground(
//                    NOTIFICATION_ID,
//                    notification,
//                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
//                )
//            } else {
//                startForeground(NOTIFICATION_ID, notification)
//            }
//
//            val attrs = AudioAttributes.Builder()
//            .setUsage(AudioAttributes.USAGE_ALARM)
//            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//            .build()
//
//        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
//        focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
//            .setAudioAttributes(attrs)
//            .build()
//        audioManager?.requestAudioFocus(focusRequest!!)
//
//        mediaPlayer = MediaPlayer().apply {
//            setAudioAttributes(attrs)
//            setDataSource(this@AdhanPlaybackService, adhanUri())
//            setOnCompletionListener { stopPlayback() }
//            setOnErrorListener { _, _, _ -> stopPlayback(); true }
//            prepare()
//            start()
//        }
//    }
//
//    private fun stopPlayback() {
//        mediaPlayer?.apply { if (isPlaying) stop(); release() }
//        mediaPlayer = null
//        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
//        stateHolder.setIdle()
//        stopForeground(STOP_FOREGROUND_REMOVE)
//        stopSelf()
//    }
//
//    private fun adhanUri(): Uri = "android.resource://$packageName/${R.raw.adhan}".toUri()
//
//    private fun buildNotification(prayerName: String): Notification {
//        val stopIntent =
//            Intent(this, AdhanPlaybackService::class.java).apply { action = ACTION_STOP }
//        val stopPendingIntent = PendingIntent.getService(
//            this, 0, stopIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        return NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle(getString(R.string.adhan_playing_title))
//            .setContentText(getString(R.string.adhan_playing_body, prayerName))
//            .setSmallIcon(R.drawable.ic_notification)
//            .setSilent(true)
//            .setOngoing(true)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .addAction(R.drawable.ic_stop, getString(R.string.stop), stopPendingIntent)
//            .build()
//    }
//
//    override fun onDestroy() {
//        mediaPlayer?.release()
//        mediaPlayer = null
//        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
//        super.onDestroy()
//    }
//
//    companion object {
//        const val ACTION_START = "com.mhq.salati.action.START_ADHAN"
//        const val ACTION_STOP = "com.mhq.salati.action.STOP_ADHAN"
//        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
//        private const val NOTIFICATION_ID = 501
//        const val CHANNEL_ID = "adhan_playback_channel"
//    }
//}