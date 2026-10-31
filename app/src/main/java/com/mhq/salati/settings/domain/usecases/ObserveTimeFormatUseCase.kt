package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** Emits the chosen [TimeFormat], and again only when it actually changes. */
class ObserveTimeFormatUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<TimeFormat> =
        settingsRepository.observeSettings()
            .map { it.timeFormat }
            .distinctUntilChanged()
}
