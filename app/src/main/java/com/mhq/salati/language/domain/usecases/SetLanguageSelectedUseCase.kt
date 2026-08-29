package com.mhq.salati.language.domain.usecases

import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class SetLanguageSelectedUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(isSelected: Boolean) =
        settingsRepository.setLanguageSelected(isSelected)
}