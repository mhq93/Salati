package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateCalculationMethodUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(method: CalculationMethod) = repository.setCalculationMethod(method)
}