package com.mhq.salati.domain.usecases.location

import com.mhq.salati.domain.model.location.SavedLocation
import com.mhq.salati.domain.repo.location.LocationRepository
import javax.inject.Inject

class FetchAndSaveLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): SavedLocation = locationRepository.fetchAndSaveLocation()
}