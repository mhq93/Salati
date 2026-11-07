package com.mhq.salati.permissions.domain.repo

/** What the app remembers about asking for the location permission. */
interface LocationPermissionRepository {
    suspend fun wasRequested(): Boolean
    suspend fun markRequested()
}