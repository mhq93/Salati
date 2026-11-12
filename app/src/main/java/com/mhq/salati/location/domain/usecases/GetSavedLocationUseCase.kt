package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The app's saved prayer location, live.
 *
 * Emits null when no place is saved;
 * re-emits whenever the saved place changes (including localization updates).
 */
class GetSavedLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<SavedLocation?> =
        locationRepository.savedLocation
}