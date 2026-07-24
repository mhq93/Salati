package com.mhq.salati.domain.usecases.settings

import com.mhq.salati.domain.repo.settings.SettingsRepository
import javax.inject.Inject

class UpdateHijriDateOffsetUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(offset: Int) = repository.setHijriDateOffset(offset)
}