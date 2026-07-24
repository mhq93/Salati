package com.mhq.salati.domain.usecases.settings

import com.mhq.salati.domain.model.settings.ThemeMode
import com.mhq.salati.domain.repo.settings.SettingsRepository
import javax.inject.Inject

class UpdateThemeModeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(mode: ThemeMode) = repository.setThemeMode(mode)
}