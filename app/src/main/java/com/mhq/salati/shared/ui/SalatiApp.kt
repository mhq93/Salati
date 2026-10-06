package com.mhq.salati.shared.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mhq.salati.R
import com.mhq.salati.alarms.ui.screens.CustomAlarmsContainer
import com.mhq.salati.home.ui.screens.HomeContainer
import com.mhq.salati.language.ui.components.LanguageSelectionDialog
import com.mhq.salati.locationpicker.ui.screens.LocationPickerContainer
import com.mhq.salati.onboarding.ui.screens.OnboardingContainer
import com.mhq.salati.prayertracker.ui.screens.PrayerTrackerContainer
import com.mhq.salati.qibla.ui.screens.QiblaContainer
import com.mhq.salati.settings.ui.screens.SettingsContainer
import com.mhq.salati.shared.ui.navigation.BottomNavBar
import com.mhq.salati.shared.ui.navigation.BottomNavBarItem
import com.mhq.salati.shared.ui.navigation.NavigationScreen
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.splash.presentation.contract.SplashContract
import com.mhq.salati.splash.presentation.viewmodel.SplashViewModel

/**
 * App root composable: gates on splash/language-selection state, then hosts the bottom-nav
 * screens behind a single [NavHost] + [BottomNavBar].
 *
 * [currentRoute] is computed here (rather than below the [Scaffold], where it used to live)
 * specifically so it can be passed into [QiblaContainer] as `isActiveTab` — the Qibla screen
 * needs to know whether it's the currently visible tab to pause/resume its compass sensor.
 */
@Composable
fun SalatiApp() {
    val splashViewModel: SplashViewModel = hiltViewModel()
    val splashState by splashViewModel.state.collectAsState()
    val context = LocalContext.current

    // Splash-only errors (first-run language selection failed). Rare, and
    // no persistent snackbar host exists yet at this point, so a toast is fine.
    LaunchedEffect(splashViewModel) {
        splashViewModel.effect.collect { effect ->
            when (effect) {
                is SplashContract.Effect.ShowError -> {
                    Toast.makeText(
                        context,
                        effect.message.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // 1. Show loading (AndroidX Splash Screen API handles the native splash)
    if (splashState.isLoading) {
        return
    }

    // 2. Show Dialog if not selected
    if (!splashState.isLanguageSelected) {
        LanguageSelectionDialog(
            onLanguageSelected = { selectedLanguage ->
                splashViewModel.onIntent(
                    SplashContract.Intent.SelectInitialLanguage(
                        selectedLanguage
                    )
                )
            },
            onDismissRequest = {
                // Forced choice: do nothing. Back button and outside clicks are ignored.
            }
        )
    } else {
        // 3. Render App Content (This will now execute after the Activity recreates)
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        val startRoute: String = splashState.startDestination?.route ?: NavigationScreen.Onboarding.route

        val items = listOf(
            BottomNavBarItem(NavigationScreen.Home.route, stringResource(R.string.home), Icons.Default.Home),
            BottomNavBarItem(
                NavigationScreen.Qibla.route,
                stringResource(R.string.qibla),
                Icons.Default.Explore
            ),
            BottomNavBarItem(
                NavigationScreen.PrayerTracker.route,
                stringResource(R.string.tracker),
                Icons.Filled.CheckCircle
            ),
            BottomNavBarItem(
                NavigationScreen.Alarms.route,
                stringResource(R.string.alarms),
                Icons.Default.Alarm
            ),
            BottomNavBarItem(
                NavigationScreen.Settings.route,
                stringResource(R.string.settings),
                Icons.Default.Settings
            )
        )

        val bottomBarRoutes = items.map { it.route }

        Box(modifier = Modifier.fillMaxSize()) {
            // Computed here (not below the Scaffold) so QiblaContainer can read
            // currentRoute for isActiveTab — see class doc above.
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val currentRoute = items.firstOrNull { item ->
                currentDestination?.hierarchy?.any { it.route == item.route } == true
            }?.route

            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { paddingValues ->
                CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                    NavHost(
                        navController = navController,
                        startDestination = startRoute,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(NavigationScreen.Onboarding.route) {
                            OnboardingContainer(
                                onFinished = {
                                    navController.navigate(NavigationScreen.Home.route) {
                                        popUpTo(NavigationScreen.Onboarding.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(NavigationScreen.Home.route) { HomeContainer() }
                        composable(NavigationScreen.LocationPicker.route) {
                            LocationPickerContainer(
                                onLocationSaved = { navController.popBackStack() },
                                onBackClicked = { navController.popBackStack() }
                            )
                        }
                        composable(NavigationScreen.PrayerTracker.route) { PrayerTrackerContainer() }
                        composable(NavigationScreen.Alarms.route) { CustomAlarmsContainer() }
                        composable(NavigationScreen.Settings.route) { SettingsContainer() }
                        composable(NavigationScreen.Qibla.route) {
                            QiblaContainer(
                                onNavigateToLocationPicker = { navController.navigate(NavigationScreen.LocationPicker.route) },
                                isActiveTab = currentRoute == NavigationScreen.Qibla.route
                            )
                        }
                    }
                }
            }

            // Floating bottom nav, drawn over the Scaffold content
            if (currentRoute != null && currentDestination?.route in bottomBarRoutes) {
                BottomNavBar(
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
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}