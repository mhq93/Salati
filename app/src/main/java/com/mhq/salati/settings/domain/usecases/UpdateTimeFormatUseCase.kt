package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateTimeFormatUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(format: TimeFormat) = settingsRepository.setTimeFormat(format)
}
