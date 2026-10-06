package com.mhq.salati.home.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
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
import com.mhq.salati.permissions.ui.rememberExactAlarmPermissionLauncher
import com.mhq.salati.permissions.ui.rememberLocationPermissionLauncher
import com.mhq.salati.permissions.ui.rememberLocationServicesEnabled
import com.mhq.salati.permissions.ui.rememberNotificationPermissionLauncher
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.asString
import kotlinx.coroutines.launch

@SuppressLint("InlinedApi", "NewApi")
@Composable
fun HomeContainer(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val locationServicesEnabled by rememberLocationServicesEnabled()

    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
        onGranted = {
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
        },
        onDenied = { permanentlyDenied ->
            homeViewModel.onIntent(
                HomeContract.Intent.LocationPermissionDenied(permanentlyDenied)
            )
        }
    )

    val notificationPermissionLauncher = rememberNotificationPermissionLauncher(
        onGranted = {
            homeViewModel.onIntent(
                HomeContract.Intent.NotificationPermissionResult(granted = true)
            )
        },
        onDenied = {
            homeViewModel.onIntent(
                HomeContract.Intent.NotificationPermissionResult(granted = false)
            )
        }
    )

    val exactAlarmLauncher = rememberExactAlarmPermissionLauncher(
        onResult = {
            homeViewModel.onIntent(HomeContract.Intent.RecheckSystemPermissions)
        }
    )

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.RequestLocationPermission -> {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                is HomeContract.Effect.NavigateToAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
                is HomeContract.Effect.NavigateToLocationSettings -> {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                is HomeContract.Effect.RequestExactAlarmPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        exactAlarmLauncher.launch(intent)
                    } else {
                        homeViewModel.onIntent(HomeContract.Intent.RecheckSystemPermissions)
                    }
                }
                is HomeContract.Effect.RequestNotificationPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        homeViewModel.onIntent(HomeContract.Intent.NotificationPermissionResult(granted = true))
                    }
                }
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
                homeViewModel.onIntent(HomeContract.Intent.ScreenResumed)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(locationServicesEnabled) {
        homeViewModel.onIntent(
            HomeContract.Intent.LocationServicesToggled(enabled = locationServicesEnabled)
        )
    }

    LaunchedEffect(Unit) {
        homeViewModel.onIntent(HomeContract.Intent.LoadPrayerTimes)
    }

    HomeContent(
        state = state,
        onIntent = homeViewModel::onIntent
    )
}