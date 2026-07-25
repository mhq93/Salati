package com.mhq.salati.domain.model.alarms

sealed interface AdhanPlaybackState {
    data object Idle : AdhanPlaybackState
    data class Playing(val prayerName: String) : AdhanPlaybackState
}