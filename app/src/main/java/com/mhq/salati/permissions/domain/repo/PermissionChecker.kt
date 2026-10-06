package com.mhq.salati.permissions.domain.repo

interface PermissionChecker {
    fun hasLocationPermission(): Boolean
    fun hasNotificationPermission(): Boolean
    fun canScheduleExactAlarms(): Boolean
}