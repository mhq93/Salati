package com.mhq.salati.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.presentation.common.location.HandleLocationPermissionEffects
import com.mhq.salati.presentation.common.location.rememberLocationPermissionLauncher
import com.mhq.salati.presentation.common.notifications.rememberNotificationPermissionLauncher

@Composable
fun HomeContainer(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val currentState by rememberUpdatedState(state)

    HandleLocationPermissionEffects(homeViewModel.permissionEffect)

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
        onGranted = { homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted) },
        onDenied = { permanentlyDenied ->
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionDenied(permanentlyDenied))
        }
    )

    val notificationPermissionLauncher = rememberNotificationPermissionLauncher(
        onGranted = { /* proceed, e.g. mark alarms enabled */ },
        onDenied = { /* show rationale or leave notifications off */ }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME &&
                (currentState.locationPermission.permanentlyDenied || currentState.locationPermission.servicesDisabled)
            ) {
                homeViewModel.onIntent(HomeContract.Intent.Retry)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(Unit) {
        homeViewModel.onIntent(HomeContract.Intent.LoadPrayerTimes)
    }

    LaunchedEffect(state.locationPermission.required) {
        if (state.locationPermission.required) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
            } else {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    HomeContent(
        state = state,
        onIntent = homeViewModel::onIntent
    )
}