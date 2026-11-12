package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import javax.inject.Inject

/**
 * Stores [location] as the place the app prays by.
 *
 * Used both for genuinely manual picks (search, map) and for saving the automatic lookup's result,
 * which is why the name says "manual" but the callers are broader.
 */
class SaveManualLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    /** @param location the place to save, replacing any previous one */
    suspend operator fun invoke(location: SavedLocation) {
        locationRepository.saveManualLocation(location)
    }
}