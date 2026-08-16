package com.mhq.salati.shared

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.screens.CustomAlarmsContainer
import com.mhq.salati.home.presentation.screens.HomeContainer
import com.mhq.salati.locationpicker.presentation.screens.LocationPickerContainer
import com.mhq.salati.onboarding.presentation.screens.OnboardingContainer
import com.mhq.salati.prayertracker.presentation.screens.PrayerTrackerContainer
import com.mhq.salati.qibla.presentation.screens.QiblaContainer
import com.mhq.salati.settings.presentation.screens.SettingsContainer
import com.mhq.salati.shared.navigation.AnimatedBottomNavBar
import com.mhq.salati.shared.navigation.BottomNavItem
import com.mhq.salati.shared.navigation.Screen
import com.mhq.salati.shared.presentation.components.LocalSnackbarHostState
import com.mhq.salati.shared.presentation.screens.AwaitingLocationPermissions

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SalatiApp(startDestination: Screen) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val items = listOf(
        BottomNavItem(Screen.Home.route, stringResource(R.string.home), Icons.Default.Home),
        BottomNavItem(Screen.Qibla.route, stringResource(R.string.qibla), Icons.Default.Explore),
        BottomNavItem(Screen.PrayerTracker.route, stringResource(R.string.tracker), Icons.Filled.CheckCircle),
        BottomNavItem(Screen.Alarms.route, stringResource(R.string.alarms), Icons.Default.Alarm),
        BottomNavItem(Screen.Settings.route, stringResource(R.string.settings), Icons.Default.Settings)
    )

    val bottomBarRoutes = items.map { it.route }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
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
                    QiblaContainer(onNavigateToLocationPicker = { navController.navigate(Screen.LocationPicker.route) })
                }
                composable(Screen.LocationPicker.route) {
                    LocationPickerContainer(
                        onLocationSaved = { navController.popBackStack() },
                        onBackClicked = { navController.popBackStack() }
                    )
                }
                composable(Screen.PrayerTracker.route) {
                    PrayerTrackerContainer()
                }
                composable(Screen.Alarms.route) {
                    CustomAlarmsContainer()
                }
                composable(Screen.Settings.route) {
                    SettingsContainer()
                }
            }
        }
    }
}