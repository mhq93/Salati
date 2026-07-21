package com.mhq.salati.domain.usecases.location

import com.mhq.salati.domain.model.location.SavedLocation
import com.mhq.salati.domain.repo.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<SavedLocation?> = locationRepository.savedLocation
}