package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateHijriDateOffsetUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(offset: Int) = repository.setHijriDateOffset(offset)
}