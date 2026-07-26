package com.mhq.salati.adhan.data

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdhanPlaybackStateHolder @Inject constructor() {
    private val _state = MutableStateFlow<AdhanPlaybackState>(AdhanPlaybackState.Idle)
    val state: StateFlow<AdhanPlaybackState> = _state.asStateFlow()

    fun setPlaying(prayerName: String) { _state.value = AdhanPlaybackState.Playing(prayerName) }
    fun setIdle() { _state.value = AdhanPlaybackState.Idle }
}