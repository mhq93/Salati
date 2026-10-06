package com.mhq.salati.permissions.data.repoimpl

import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.permissions.datasource.device.PermissionDataSource
import javax.inject.Inject

class PermissionCheckerImpl @Inject constructor(
    private val permissionDataSource: PermissionDataSource
) : PermissionChecker {

    override fun hasLocationPermission(): Boolean =
        permissionDataSource.hasLocationPermission()

    override fun hasNotificationPermission(): Boolean =
        permissionDataSource.hasNotificationPermission()

    override fun canScheduleExactAlarms(): Boolean =
        permissionDataSource.canScheduleExactAlarms()
}