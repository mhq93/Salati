package com.mhq.salati.permissions.data.repoimpl

import com.mhq.salati.permissions.datasource.preferences.LocationPermissionDataStore
import com.mhq.salati.permissions.domain.repo.LocationPermissionRepository
import javax.inject.Inject

class LocationPermissionRepoImpl @Inject constructor(
    private val dataStore: LocationPermissionDataStore
) : LocationPermissionRepository {

    override suspend fun wasRequested(): Boolean = dataStore.wasRequested()

    override suspend fun markRequested() = dataStore.markRequested()
}