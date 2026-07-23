package com.mhq.salati.domain.permissions

interface PermissionChecker {
    fun hasLocationPermission(): Boolean
    fun hasNotificationPermission(): Boolean
    fun canScheduleExactAlarms(): Boolean
}