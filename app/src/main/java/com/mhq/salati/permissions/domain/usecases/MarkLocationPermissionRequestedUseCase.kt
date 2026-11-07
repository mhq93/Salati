package com.mhq.salati.permissions.domain.usecases

import com.mhq.salati.permissions.domain.repo.LocationPermissionRepository
import javax.inject.Inject

class MarkLocationPermissionRequestedUseCase @Inject constructor(
    private val repository: LocationPermissionRepository
) {
    suspend operator fun invoke() = repository.markRequested()
}