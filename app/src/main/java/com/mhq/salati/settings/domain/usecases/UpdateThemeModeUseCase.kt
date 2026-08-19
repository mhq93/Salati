package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateThemeModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(mode: ThemeMode) = settingsRepository.setThemeMode(mode)
}