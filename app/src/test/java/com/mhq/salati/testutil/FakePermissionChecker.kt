package com.mhq.salati.testutil

import com.mhq.salati.permissions.domain.repo.PermissionChecker

class FakePermissionChecker(private val granted: Boolean) : PermissionChecker {
    override fun hasLocationPermission() = granted
    override fun hasNotificationPermission() = granted
    override fun canScheduleExactAlarms() = granted
}