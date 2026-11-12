package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.model.SavedLocation
import kotlinx.coroutines.flow.Flow

/**
 * The app's saved prayer location, implemented in Data over persistent storage.
 *
 * A null [savedLocation] means no place is saved and lookups should use the device position.
 */
interface LocationRepository {
    /** The saved place, or null when none is saved; re-emits on every change. */
    val savedLocation: Flow<SavedLocation?>

    /** Stores [location] as the place the app prays by, replacing the previous one. */
    suspend fun saveManualLocation(location: SavedLocation)

    /** Forgets the saved place; [savedLocation] emits null afterwards. */
    suspend fun clearSavedLocation()
}