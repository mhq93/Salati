package com.mhq.salati.domain.repo.location

import com.mhq.salati.domain.model.location.SavedLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val savedLocation: Flow<SavedLocation?>
    suspend fun fetchAndSaveLocation(): SavedLocation
}