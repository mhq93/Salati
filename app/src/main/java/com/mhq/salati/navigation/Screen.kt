package com.mhq.salati.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Qibla : Screen("qibla")
    data object Settings : Screen("settings")
}