package com.mhq.salati.adhan.presentation.presenter

import com.mhq.salati.adhan.domain.model.AdhanPlaybackState
import com.mhq.salati.adhan.domain.usecases.UpdateAdhanPlaybackStateUseCase
import com.mhq.salati.settings.presentation.mapper.toAdhanSoundUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.shared.domain.usecases.ResolvePrayerNameUseCase
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import javax.inject.Inject

/**
 * What the playback service reports about itself,
 * and how it reads the keys it receives in its Intent.
 */
class AdhanPlaybackPresenter @Inject constructor(
    private val updateAdhanPlaybackStateUseCase: UpdateAdhanPlaybackStateUseCase,
    private val resolvePrayerNameUseCase: ResolvePrayerNameUseCase
) {
    fun onPlaybackStarted(prayerName: String) {
        updateAdhanPlaybackStateUseCase(AdhanPlaybackState.Playing(prayerName))
    }

    fun onPlaybackStopped() {
        updateAdhanPlaybackStateUseCase(AdhanPlaybackState.Idle)
    }

    /** The prayer an Intent key stands for, or null when the key is unknown. */
    fun prayerNameFor(prayerKey: String): PrayerNameUi? =
        resolvePrayerNameUseCase(prayerKey)?.toUi()

    /** The adhan sound an Intent key stands for; the default sound when it is missing or unknown. */
    fun adhanSoundFor(soundKey: String?): AdhanSoundUi = soundKey.toAdhanSoundUi()
}