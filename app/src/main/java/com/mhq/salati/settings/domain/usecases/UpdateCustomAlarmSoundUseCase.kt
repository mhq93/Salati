package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateCustomAlarmSoundUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(sound: CustomAlarmSound) = settingsRepository.setCustomAlarmSound(sound)
}
