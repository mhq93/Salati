package com.mhq.salati.shared.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    //object AwaitingLocationPermissions : Screen("awaiting_location_permissions")
    data object Home : Screen("home")
    data object Qibla : Screen("qibla")
    data object PrayerTracker : Screen("prayer_tracker")
    data object Alarms : Screen("alarms")
    data object Settings : Screen("settings")
    data object LocationPicker : Screen("location_picker")
}