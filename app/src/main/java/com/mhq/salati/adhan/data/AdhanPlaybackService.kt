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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class AdhanPlaybackService : Service() {

    @Inject
    lateinit var stateHolder: AdhanPlaybackStateHolder

    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var focusRequest: AudioFocusRequest? = null
    private var currentIsMinorTiming: Boolean = false
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startPlayback(
                prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "Prayer",
                isMinorTiming = intent.getBooleanExtra(EXTRA_IS_MINOR_TIMING, false)
            )
            ACTION_STOP -> stopPlayback()
        }
        return START_NOT_STICKY
    }

    private fun startPlayback(prayerName: String, isMinorTiming: Boolean) {
        currentIsMinorTiming = isMinorTiming

        val notification = buildNotification(prayerName, isMinorTiming)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
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
            setDataSource(this@AdhanPlaybackService, soundUri(isMinorTiming))
            setOnPreparedListener { it.start() }
            setOnCompletionListener { onPlaybackCompleted() }
            setOnErrorListener { _, _, _ -> stopPlayback(); true }
            prepareAsync()
        }
    }

    private fun onPlaybackCompleted() {
        mediaPlayer?.apply { if (isPlaying) stop(); release() }
        mediaPlayer = null
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
    }

    private fun stopPlayback() {
        mediaPlayer?.apply { if (isPlaying) stop(); release() }
        mediaPlayer = null
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        stateHolder.setIdle()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun soundUri(isMinorTiming: Boolean): Uri {
        val resId = if (isMinorTiming) R.raw.alert else R.raw.adhan
        return "android.resource://$packageName/$resId".toUri()
    }

    private fun buildNotification(prayerName: String, isMinorTiming: Boolean): Notification {
        val stopIntent = Intent(this, AdhanPlaybackService::class.java).apply { action = ACTION_STOP }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = if (isMinorTiming) {
            getString(R.string.minor_timing_playing_title, prayerName)
        } else {
            getString(R.string.adhan_playing_title)
        }
        val body = if (isMinorTiming) {
            getString(R.string.minor_timing_playing_body, prayerName)
        } else {
            getString(R.string.adhan_playing_body, prayerName)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
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
        serviceScope.cancel()
        super.onDestroy()
    }

    companion object {
        const val ACTION_START = "com.mhq.salati.action.START_ADHAN"
        const val ACTION_STOP = "com.mhq.salati.action.STOP_ADHAN"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        private const val NOTIFICATION_ID = 501
        const val CHANNEL_ID = "adhan_playback_channel"
    }
}