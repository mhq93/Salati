package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateMadhabUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(madhab: Madhab) = repository.setMadhab(madhab)
}