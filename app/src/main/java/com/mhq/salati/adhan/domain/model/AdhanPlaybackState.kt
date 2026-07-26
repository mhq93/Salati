package com.mhq.salati.adhan.domain.model

sealed interface AdhanPlaybackState {
    data object Idle : AdhanPlaybackState
    data class Playing(val prayerName: String) : AdhanPlaybackState
}