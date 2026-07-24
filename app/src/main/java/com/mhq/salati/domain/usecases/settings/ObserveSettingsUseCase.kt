package com.mhq.salati.domain.usecases.settings

import com.mhq.salati.domain.model.settings.AppSettings
import com.mhq.salati.domain.repo.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<AppSettings> = repository.observeSettings()
}













