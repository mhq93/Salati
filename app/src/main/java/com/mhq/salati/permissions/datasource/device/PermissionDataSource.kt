package com.mhq.salati.permissions.datasource.device

interface PermissionDataSource {
    fun hasLocationPermission(): Boolean
    fun hasNotificationPermission(): Boolean
    fun canScheduleExactAlarms(): Boolean
}