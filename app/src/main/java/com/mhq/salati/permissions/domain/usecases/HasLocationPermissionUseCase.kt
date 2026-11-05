package com.mhq.salati.permissions.domain.usecases

import com.mhq.salati.permissions.domain.repo.PermissionChecker
import javax.inject.Inject

class HasLocationPermissionUseCase @Inject constructor(
    private val permissionChecker: PermissionChecker
) { operator fun invoke(): Boolean = permissionChecker.hasLocationPermission() }