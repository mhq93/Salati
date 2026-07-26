package com.mhq.salati.shared

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mhq.salati.home.presentation.screens.HomeContainer
import com.mhq.salati.onboarding.presentation.screens.OnboardingContainer
import com.mhq.salati.qibla.presentation.screens.QiblaContainer
import com.mhq.salati.settings.presentation.screens.SettingsContainer
import com.mhq.salati.shared.navigation.AnimatedBottomNavBar
import com.mhq.salati.shared.navigation.BottomNavItem
import com.mhq.salati.shared.navigation.Screen
import com.mhq.salati.shared.presentation.screens.AwaitingLocationPermissions

@Composable
fun SalatiApp(startDestination: Screen) {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home),
        BottomNavItem(Screen.Qibla.route, "Qibla", Icons.Default.Explore),
        BottomNavItem(Screen.Settings.route, "Settings", Icons.Default.Settings)
    )

    val bottomBarRoutes = items.map { it.route }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val currentRoute = items.firstOrNull { item ->
                currentDestination?.hierarchy?.any { it.route == item.route } == true
            }?.route

            // Only show bottom bar on main tab screens
            if (currentRoute != null && currentDestination?.route in bottomBarRoutes) {
                AnimatedBottomNavBar(
                    items = items,
                    selectedRoute = currentRoute,
                    onItemSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingContainer(
                    onFinished = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.AwaitingLocationPermissions.route) {
                AwaitingLocationPermissions()
            }
            composable(Screen.Home.route) {
                HomeContainer()
            }
            composable(Screen.Qibla.route) {
                QiblaContainer()
            }
            composable(Screen.Settings.route) {
                SettingsContainer()
            }
        }
    }
}

//@Composable
//fun SalatiApp() {
//    val navController = rememberNavController()
//
//    val items = listOf(
//        BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home),
//        BottomNavItem(Screen.Qibla.route, "Qibla", Icons.Default.Explore),
//        BottomNavItem(Screen.Settings.route, "Settings", Icons.Default.Settings)
//    )
//
//    Scaffold(
//        bottomBar = {
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentDestination = navBackStackEntry?.destination
//            val currentRoute = items.firstOrNull { item ->
//                currentDestination?.hierarchy?.any { it.route == item.route } == true
//            }?.route ?: Screen.Home.route
//
//            AnimatedBottomNavBar(
//                items = items,
//                selectedRoute = currentRoute,
//                onItemSelected = { route ->
//                    navController.navigate(route) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    ) { _ ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.Home.route
//        ) {
//            composable(Screen.Home.route) {
//                HomeContainer()
//            }
//            composable(Screen.Qibla.route) {
//                QiblaContainer()
//            }
//            composable(Screen.Settings.route) {
//                SettingsContainer()
//            }
//        }
//    }
//}