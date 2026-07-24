package com.mhq.salati.domain.usecases.settings

import com.mhq.salati.domain.model.settings.CalculationMethod
import com.mhq.salati.domain.repo.settings.SettingsRepository
import javax.inject.Inject

class UpdateCalculationMethodUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(method: CalculationMethod) = repository.setCalculationMethod(method)
}