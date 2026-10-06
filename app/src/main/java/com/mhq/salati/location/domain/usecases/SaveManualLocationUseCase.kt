package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import javax.inject.Inject

class SaveManualLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(location: SavedLocation) {
        locationRepository.saveManualLocation(location)
    }
}