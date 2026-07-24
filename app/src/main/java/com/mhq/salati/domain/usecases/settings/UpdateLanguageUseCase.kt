package com.mhq.salati.domain.usecases.settings

import com.mhq.salati.domain.model.settings.AppLanguage
import com.mhq.salati.domain.repo.settings.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage) = repository.setLanguage(language)
}