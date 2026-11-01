package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.mapper.toDomain
import com.mhq.salati.location.data.mapper.toStored
import com.mhq.salati.location.datasource.preferences.LocationDataStore
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(
    private val locationDataStore: LocationDataStore
) : LocationRepository {

    override val savedLocation: Flow<SavedLocation?> =
        locationDataStore.storedLocation.map { it?.toDomain() }

    override suspend fun saveManualLocation(location: SavedLocation) {
        locationDataStore.save(location.toStored())
    }
}