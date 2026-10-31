package com.mhq.salati.shared.ui.navigation

import com.mhq.salati.splash.presentation.model.StartDestinationUi

sealed class NavigationScreen(val route: String) {
    object Onboarding : NavigationScreen("onboarding")
    data object Home : NavigationScreen("home")
    data object Qibla : NavigationScreen("qibla")
    data object PrayerTracker : NavigationScreen("prayer_tracker")
    data object Alarms : NavigationScreen("alarms")
    data object Settings : NavigationScreen("settings")
    data object LocationPicker : NavigationScreen("location_picker")
}

fun StartDestinationUi.toNavigationScreen(): NavigationScreen = when (this) {
    StartDestinationUi.ONBOARDING -> NavigationScreen.Onboarding
    StartDestinationUi.HOME -> NavigationScreen.Home
}