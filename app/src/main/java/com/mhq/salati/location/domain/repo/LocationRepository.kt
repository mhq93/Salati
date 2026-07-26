package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.model.SavedLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val savedLocation: Flow<SavedLocation?>
    suspend fun fetchAndSaveLocation(): SavedLocation
}