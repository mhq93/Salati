package com.mhq.salati.adhan.ui.service

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
import com.mhq.salati.adhan.presentation.presenter.AdhanPlaybackPresenter
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.ui.labelRes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class AdhanPlaybackService : Service() {

    @Inject
    lateinit var adhanPlaybackPresenter: AdhanPlaybackPresenter
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var focusRequest: AudioFocusRequest? = null
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var autoDismissJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startPlayback(
                prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "Prayer",
                isMinorTiming = intent.getBooleanExtra(EXTRA_IS_MINOR_TIMING, false),
                isMuted = intent.getBooleanExtra(EXTRA_IS_MUTED, false),
                adhanSound = intent.getStringExtra(EXTRA_ADHAN_SOUND)
                    ?.let { runCatching { AdhanSound.valueOf(it) }.getOrNull() }
                    ?: AdhanSound.DEFAULT
            )

            ACTION_STOP -> stopPlayback()
        }
        return START_NOT_STICKY
    }

    private fun startPlayback(
        prayerName: String,
        isMinorTiming: Boolean,
        isMuted: Boolean,
        adhanSound: AdhanSound
    ) {
        releasePlayer()

        val notification = buildNotification(prayerName, isMinorTiming)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }

        adhanPlaybackPresenter.onPlaybackStarted(prayerName)

        // NEW — muted prayers have nothing to play and no completion callback
        // to end them, so the foreground notification would otherwise stay
        // stuck until the user manually taps Stop. Auto-dismiss shortly instead.
        if (isMuted) {
            // Muted prayers have nothing to play and no completion callback,
            // so dismiss the notification shortly instead of leaving it stuck.
            autoDismissJob = serviceScope.launch {
                delay(MUTED_AUTO_DISMISS_MS.milliseconds)
                stopPlayback()
            }
            return
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
            .setAudioAttributes(audioAttributes)
            .build()

        val focusResult = audioManager?.requestAudioFocus(focusRequest!!)
        if (focusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stopPlayback()
            return
        }


        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(audioAttributes)
            setDataSource(this@AdhanPlaybackService, soundUri(isMinorTiming, adhanSound))
            setOnPreparedListener { it.start() }
            setOnCompletionListener { onPlaybackCompleted() }
            setOnErrorListener { _, _, _ -> stopPlayback(); true }
            prepareAsync()
        }
    }

    // NEW — shared cleanup for mediaPlayer + audio focus, used by both
    // onPlaybackCompleted() and stopPlayback() (and now startPlayback()'s guard)
    // so the two paths can't drift out of sync again.
    private fun releasePlayer() {
        autoDismissJob?.cancel()
        autoDismissJob = null
        mediaPlayer?.apply { if (isPlaying) stop(); release() }
        mediaPlayer = null
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        audioManager = null
    }

    private fun onPlaybackCompleted() {
        stopPlayback()
    }

    private fun stopPlayback() {
        releasePlayer()
        adhanPlaybackPresenter.onPlaybackStopped()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun soundUri(isMinorTiming: Boolean, adhanSound: AdhanSound): Uri {
        val resId = if (isMinorTiming) {
            R.raw.minor_timing_alert
        } else when (adhanSound) {
            AdhanSound.DEFAULT -> R.raw.default_beep
            AdhanSound.EGYPT -> R.raw.adhan_egypt
            AdhanSound.MAKKAH -> R.raw.adhan_makkah
            AdhanSound.MADINAH -> R.raw.adhan_madinah
            AdhanSound.SILENT -> R.raw.default_beep
        }
        return "android.resource://$packageName/$resId".toUri()
    }

    private fun buildNotification(prayerName: String, isMinorTiming: Boolean): Notification {
        val stopIntent = Intent(
            this,
            AdhanPlaybackService::class.java
        ).apply { action = ACTION_STOP }

        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val localizedPrayerName = PrayerName
            .fromStorageKey(prayerName)?.let { getString(it.labelRes) } ?: prayerName

        val title = if (isMinorTiming) {
            getString(R.string.minor_timing_playing_title, localizedPrayerName)
        } else {
            getString(R.string.adhan_playing_title)
        }

        val body = if (isMinorTiming) {
            getString(R.string.minor_timing_playing_body, localizedPrayerName)
        } else {
            getString(R.string.adhan_playing_body, localizedPrayerName)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setSilent(true)
            .setOngoing(true)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
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
        private const val NOTIFICATION_ID = 501
        private const val MUTED_AUTO_DISMISS_MS = 5_000L
        const val ACTION_START = "com.mhq.salati.action.START_ADHAN"
        const val ACTION_STOP = "com.mhq.salati.action.STOP_ADHAN"
        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_IS_MINOR_TIMING = "extra_is_minor_timing"
        const val EXTRA_IS_MUTED = "extra_is_muted"
        const val EXTRA_ADHAN_SOUND = "extra_adhan_sound"
        const val CHANNEL_ID = "adhan_playback_channel"
    }
}