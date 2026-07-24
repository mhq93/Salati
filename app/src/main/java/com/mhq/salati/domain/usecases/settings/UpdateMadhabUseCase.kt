package com.mhq.salati.domain.usecases.settings

import com.mhq.salati.domain.model.settings.Madhab
import com.mhq.salati.domain.repo.settings.SettingsRepository
import javax.inject.Inject

class UpdateMadhabUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(madhab: Madhab) = repository.setMadhab(madhab)
}