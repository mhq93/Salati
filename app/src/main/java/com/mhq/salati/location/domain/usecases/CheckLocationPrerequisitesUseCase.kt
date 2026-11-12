package com.mhq.salati.location.domain.usecases

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import javax.inject.Inject

/**
 * Checks everything an automatic location lookup needs, in one pass:
 * internet, location services and the location permission.
 *
 * Evaluates all three conditions; nothing stops at the first one that fails.
 */
class CheckLocationPrerequisitesUseCase @Inject constructor(
    private val connectivityChecker: ConnectivityChecker,
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker
) {
    /** @return the three checks, each independently true or false */
    suspend operator fun invoke(): LocationPrerequisites = LocationPrerequisites(
        hasInternet = connectivityChecker.isConnected(),
        servicesEnabled = locationProvider.isLocationEnabled(),
        permissionGranted = permissionChecker.hasLocationPermission()
    )
}