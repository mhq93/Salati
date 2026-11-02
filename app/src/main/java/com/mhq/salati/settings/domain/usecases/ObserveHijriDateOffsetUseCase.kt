package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveHijriDateOffsetUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Int> =
        settingsRepository.observeSettings()
            .map { it.hijriDateOffset }
            .distinctUntilChanged()
}