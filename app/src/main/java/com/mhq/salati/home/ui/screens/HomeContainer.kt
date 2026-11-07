package com.mhq.salati.home.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.viewmodel.HomeViewModel
import com.mhq.salati.location.ui.LocationPrerequisitesObserver
import com.mhq.salati.location.ui.rememberLocationPrerequisitesActions
import com.mhq.salati.permissions.ui.shouldShowLocationRationale
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun HomeContainer(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLocationPicker: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    val locationActions = rememberLocationPrerequisitesActions(
        onPermissionGranted = {
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
        },
        onPermissionDenied = { showRationale ->
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionDenied(showRationale))
        }
    )

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.RequestLocationPermission -> locationActions.requestPermission()
                is HomeContract.Effect.NavigateToAppSettings -> locationActions.openAppSettings()
                is HomeContract.Effect.NavigateToLocationSettings -> locationActions.openLocationSettings()
                is HomeContract.Effect.NavigateToInternetSettings -> locationActions.openInternetSettings()
                is HomeContract.Effect.NavigateToLocationPicker -> onNavigateToLocationPicker()
                is HomeContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                homeViewModel.onIntent(
                    HomeContract.Intent.ScreenResumed(context.shouldShowLocationRationale())
                )
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LocationPrerequisitesObserver(
        onServicesChanged = { enabled ->
            homeViewModel.onIntent(HomeContract.Intent.LocationServicesToggled(enabled))
        },
        onConnectivityChanged = { connected ->
            homeViewModel.onIntent(HomeContract.Intent.ConnectivityChanged(connected))
        }
    )

    LaunchedEffect(Unit) {
        homeViewModel.onIntent(HomeContract.Intent.LoadPrayerTimes)
    }

    HomeContent(
        state = state,
        onIntent = homeViewModel::onIntent
    )
}