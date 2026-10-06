package com.mhq.salati.adhan.datasource.playback

/** What the playback service is doing, as the DataSource remembers it. */
sealed interface PlaybackStatus {
    data object Idle : PlaybackStatus
    data class Playing(val prayerKey: String) : PlaybackStatus
}