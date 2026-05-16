package com.radio.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession

class RadioService : Service() {

    companion object {
        const val CHANNEL_ID = "radio_playback"
        const val NOTIFICATION_ID = 1001
        const val ACTION_PLAY = "com.radio.app.ACTION_PLAY"
        const val ACTION_STOP = "com.radio.app.ACTION_STOP"
        const val ACTION_PAUSE = "com.radio.app.ACTION_PAUSE"
        const val BROADCAST_STATE = "com.radio.app.STATE"
        const val EXTRA_URL = "url"
        const val EXTRA_STATE = "state"
        const val EXTRA_STATION_NAME = "station_name"
        const val STATE_IDLE = 0
        const val STATE_CONNECTING = 1
        const val STATE_PLAYING = 2
        const val STATE_BUFFERING = 3
        const val STATE_PAUSED = 4
    }

    private var player: ExoPlayer? = null
    private var mediaSession: MediaSession? = null
    private var currentUrl: String = ""
    private var currentStationName: String = ""
    private var isPaused = false

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val url = intent.getStringExtra(EXTRA_URL) ?: getString(R.string.stream_url)
                val name = intent.getStringExtra(EXTRA_STATION_NAME) ?: getString(R.string.default_station)
                currentStationName = name
                if (url != currentUrl || player == null) {
                    currentUrl = url
                    isPaused = false
                    startPlayback(url)
                    sendBroadcast(STATE_CONNECTING, url)
                } else if (isPaused) {
                    resumePlayback()
                }
            }
            ACTION_STOP -> {
                stopPlayback()
                sendBroadcast(STATE_IDLE, "")
            }
            ACTION_PAUSE -> {
                if (isPaused) {
                    resumePlayback()
                } else {
                    pausePlayback()
                }
            }
        }
        return START_STICKY
    }

    private fun startPlayback(url: String) {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

        player?.release()
        player = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()

        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true

        mediaSession?.release()
        mediaSession = MediaSession.Builder(this, player!!).build()

        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                updateNotification()
                when (playbackState) {
                    Player.STATE_READY -> sendBroadcast(STATE_PLAYING, currentUrl)
                    Player.STATE_BUFFERING -> sendBroadcast(STATE_BUFFERING, currentUrl)
                    Player.STATE_IDLE -> sendBroadcast(STATE_IDLE, "")
                    Player.STATE_ENDED -> sendBroadcast(STATE_IDLE, "")
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    startForeground(NOTIFICATION_ID, buildNotification())
                } else if (player?.playbackState == Player.STATE_ENDED ||
                    player?.playbackState == Player.STATE_IDLE
                ) {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                }
            }
        })

        player?.play()
    }

    private fun stopPlayback() {
        isPaused = false
        player?.stop()
        player?.release()
        player = null
        mediaSession?.release()
        mediaSession = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun pausePlayback() {
        player?.pause()
        isPaused = true
        updateNotification()
        sendBroadcast(STATE_PAUSED, currentUrl)
    }

    private fun resumePlayback() {
        player?.play()
        isPaused = false
        updateNotification()
        sendBroadcast(STATE_PLAYING, currentUrl)
    }

    private fun sendBroadcast(state: Int, url: String) {
        val intent = Intent(BROADCAST_STATE).apply {
            putExtra(EXTRA_STATE, state)
            putExtra(EXTRA_URL, url)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun updateNotification() {
        val notification = buildNotification()
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(): Notification {
        val pauseIntent = Intent(this, RadioService::class.java).apply {
            action = ACTION_PAUSE
        }
        val pausePendingIntent = PendingIntent.getService(
            this, 1, pauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(this, RadioService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val openIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val contentPendingIntent = PendingIntent.getActivity(
            this, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseIcon = if (isPaused) android.R.drawable.ic_media_play else android.R.drawable.ic_media_pause
        val playPauseText = if (isPaused) getString(R.string.play) else getString(R.string.pause)
        val statusText = if (isPaused) getString(R.string.pause) else getString(R.string.default_station)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(currentStationName.ifEmpty { getString(R.string.app_name) })
            .setContentText(statusText)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(contentPendingIntent)
            .setOngoing(!isPaused)
            .setSilent(true)
            .addAction(playPauseIcon, playPauseText, pausePendingIntent)
            .setStyle(MediaStyle()
                .setMediaSession(mediaSession?.sessionCompatToken)
                .setShowActionsInCompactView(0)
                .setShowCancelButton(true)
                .setCancelButtonIntent(stopPendingIntent))
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.channel_radio),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notificaci\u00f3n de reproducci\u00f3n de radio"
                setShowBadge(false)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        stopPlayback()
        super.onDestroy()
    }
}
