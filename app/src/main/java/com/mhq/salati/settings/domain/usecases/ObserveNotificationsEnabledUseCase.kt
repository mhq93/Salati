package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** Emits whether the user has notifications turned on, and again only when that actually changes. */
class ObserveNotificationsEnabledUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> =
        settingsRepository.observeSettings()
            .map { it.notificationsEnabled }
            .distinctUntilChanged()
}