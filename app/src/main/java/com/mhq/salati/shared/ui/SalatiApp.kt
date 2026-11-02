package com.mhq.salati.shared.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mhq.salati.R
import com.mhq.salati.alarms.ui.screens.CustomAlarmsContainer
import com.mhq.salati.home.ui.screens.HomeContainer
import com.mhq.salati.locationpicker.ui.screens.LocationPickerContainer
import com.mhq.salati.onboarding.ui.screens.OnboardingContainer
import com.mhq.salati.prayertracker.ui.screens.PrayerTrackerContainer
import com.mhq.salati.qibla.ui.screens.QiblaContainer
import com.mhq.salati.settings.ui.screens.SettingsContainer
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.navigation.BottomNavBar
import com.mhq.salati.shared.ui.navigation.BottomNavBarItem
import com.mhq.salati.shared.ui.navigation.NavigationScreen
import com.mhq.salati.shared.ui.navigation.toNavigationScreen
import com.mhq.salati.splash.presentation.viewmodel.SplashViewModel

/** Routes that appear in the bottom bar. Switching between them should feel instant. */
private val TabRoutes = setOf(
    NavigationScreen.Home.route,
    NavigationScreen.Qibla.route,
    NavigationScreen.PrayerTracker.route,
    NavigationScreen.Alarms.route,
    NavigationScreen.Settings.route
)

/** Fade used for non-tab navigation (onboarding to home, location picker). */
private const val NON_TAB_FADE_MILLIS = 200

private fun AnimatedContentTransitionScope<NavBackStackEntry>.isTabSwitch(): Boolean =
    initialState.destination.route in TabRoutes && targetState.destination.route in TabRoutes

private val ScreenEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    if (isTabSwitch())
        EnterTransition.None
    else
        fadeIn(tween(NON_TAB_FADE_MILLIS))
}

private val ScreenExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    if (isTabSwitch())
        ExitTransition.None
    else
        fadeOut(tween(NON_TAB_FADE_MILLIS))
}

/**
 * App root composable: gates on splash loading only, then hosts the bottom-nav
 * screens behind a single [NavHost] + floating bottom bar.
 *
 * First-run language selection moved to onboarding's Welcome page, so the
 * old language-dialog gate is gone.
 */
@Composable
fun SalatiApp() {
    val splashViewModel: SplashViewModel = hiltViewModel()
    val splashState by splashViewModel.state.collectAsState()

    // 1. Show loading (AndroidX Splash Screen API handles the native splash)
    if (splashState.isLoading) {
        return
    }

    // 2. Render App Content
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val startRoute: String =
        splashState.startDestination?.toNavigationScreen()?.route
            ?: NavigationScreen.Onboarding.route

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                SalatiNavHost(
                    navController = navController,
                    startRoute = startRoute,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        // Floating bottom nav, drawn over the Scaffold content
        SalatiBottomBar(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/** The navigation graph. Takes only stable inputs so it is never rebuilt on tab switches. */
@Composable
private fun SalatiNavHost(
    navController: NavHostController,
    startRoute: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = modifier,
        enterTransition = ScreenEnter,
        exitTransition = ScreenExit,
        popEnterTransition = ScreenEnter,
        popExitTransition = ScreenExit
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
                onNavigateToLocationPicker = {
                    navController.navigate(NavigationScreen.LocationPicker.route)
                }
            )
        }
    }
}

/**
 * Bottom bar that observes the back stack itself, so navigation changes recompose only this
 * composable. Slides away on non-tab screens (e.g. the location picker) instead of popping out.
 */
@Composable
private fun SalatiBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val homeLabel = stringResource(R.string.home)
    val qiblaLabel = stringResource(R.string.qibla)
    val trackerLabel = stringResource(R.string.tracker)
    val alarmsLabel = stringResource(R.string.alarms)
    val settingsLabel = stringResource(R.string.settings)

    val items = remember(homeLabel, qiblaLabel, trackerLabel, alarmsLabel, settingsLabel) {
        listOf(
            BottomNavBarItem(NavigationScreen.Home.route, homeLabel, Icons.Default.Home),
            BottomNavBarItem(NavigationScreen.Qibla.route, qiblaLabel, Icons.Default.Explore),
            BottomNavBarItem(
                NavigationScreen.PrayerTracker.route,
                trackerLabel,
                Icons.Filled.CheckCircle
            ),
            BottomNavBarItem(NavigationScreen.Alarms.route, alarmsLabel, Icons.Default.Alarm),
            BottomNavBarItem(NavigationScreen.Settings.route, settingsLabel, Icons.Default.Settings)
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedRoute = currentRoute?.takeIf { route -> items.any { it.route == route } }

    var lastTabRoute by remember { mutableStateOf(items.first().route) }
    LaunchedEffect(selectedRoute) {
        if (selectedRoute != null) lastTabRoute = selectedRoute
    }

    AnimatedVisibility(
        visible = selectedRoute != null,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut(),
        modifier = modifier
    ) {
        BottomNavBar(
            items = items,
            selectedRoute = selectedRoute ?: lastTabRoute,
            onItemSelected = { route ->
                navController.navigate(route) {
                    popUpTo(NavigationScreen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}