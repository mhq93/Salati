package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateAdhanSoundUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(sound: AdhanSound) = settingsRepository.setAdhanSound(sound)
}