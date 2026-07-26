package com.mhq.salati.home.presentation.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.permissions.exactalarm.rememberExactAlarmPermissionLauncher
import com.mhq.salati.permissions.location.LocationPermissionEffect
import com.mhq.salati.permissions.location.rememberGpsEnabled
import com.mhq.salati.permissions.location.rememberLocationPermissionLauncher
import com.mhq.salati.permissions.notifications.rememberNotificationPermissionLauncher
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeContainer(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    val gpsEnabled by rememberGpsEnabled()

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
        onGranted = {
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
        },
        onDenied = { permanentlyDenied ->
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionDenied(permanentlyDenied))
        }
    )

    val notificationPermissionLauncher = rememberNotificationPermissionLauncher(
        onGranted = { /* proceed, e.g. mark alarms enabled */ },
        onDenied = { /* show rationale or leave notifications off */ }
    )

    val exactAlarmLauncher = rememberExactAlarmPermissionLauncher(
        onResult = { /* user returned from settings; re-check on next schedule attempt */ }
    )

    // Reacts to one-shot decisions made by the ViewModel — never decides anything itself
    LaunchedEffect(Unit) {
        homeViewModel.permissionEffect.collect { effect ->
            when (effect) {
                is LocationPermissionEffect.RequestPermission -> {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                is LocationPermissionEffect.PermissionResolved -> {
                    // no-op here — the ViewModel now reacts to this itself
                }
                is LocationPermissionEffect.NavigateToAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
                is LocationPermissionEffect.NavigateToLocationSettings -> {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            }
        }
    }

    // Reacts to one-shot decisions made by the ViewModel — same rule as above,
    // now covers exact-alarm and notification permission requests too
    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.RequestExactAlarmPermission -> {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    exactAlarmLauncher.launch(intent)
                }
                is HomeContract.Effect.RequestNotificationPermission -> {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                is HomeContract.Effect.ShowError -> {
                    // TODO: surface via snackbar/toast — same mechanism as Qibla
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME &&
                (state.locationPermission.permanentlyDenied || state.locationPermission.servicesDisabled)
            ) {
                homeViewModel.onIntent(HomeContract.Intent.Retry)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(gpsEnabled) {
        if (gpsEnabled && state.locationPermission.servicesDisabled) {
            homeViewModel.onIntent(HomeContract.Intent.Retry)
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.onIntent(HomeContract.Intent.LoadPrayerTimes)
    }

    HomeContent(
        state = state,
        onIntent = homeViewModel::onIntent
    )
}