package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.repo.LocationRepository
import javax.inject.Inject

/** Forgets the saved place, so the next lookup uses the device location again. */
class ClearSavedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke() {
        locationRepository.clearSavedLocation()
    }
}