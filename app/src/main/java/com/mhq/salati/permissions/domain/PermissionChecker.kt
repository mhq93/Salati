package com.mhq.salati.permissions.domain

interface PermissionChecker {
    fun hasLocationPermission(): Boolean
    fun hasNotificationPermission(): Boolean
    fun canScheduleExactAlarms(): Boolean
}