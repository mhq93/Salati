package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage) = settingsRepository.setLanguage(language)
}