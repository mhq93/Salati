package com.mhq.salati.home.presentation.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
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
import com.mhq.salati.home.presentation.contract.HomeContract.Effect
import com.mhq.salati.home.presentation.viewmodel.HomeViewModel
import com.mhq.salati.permissions.exactalarm.rememberExactAlarmPermissionLauncher
import com.mhq.salati.permissions.location.LocationPermissionEffect
import com.mhq.salati.permissions.location.rememberLocationPermissionLauncher
import com.mhq.salati.permissions.location.rememberLocationServicesEnabled
import com.mhq.salati.permissions.notifications.rememberNotificationPermissionLauncher
import com.mhq.salati.shared.presentation.components.LocalSnackbarHostState
import com.mhq.salati.shared.presentation.components.asString
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            homeViewModel.onIntent(
                HomeContract.Intent.LocationPermissionGranted
            )
        },
        onDenied = { permanentlyDenied ->
            homeViewModel.onIntent(
                HomeContract.Intent.LocationPermissionDenied(
                    permanentlyDenied
                )
            )
        }
    )

    val notificationPermissionLauncher = rememberNotificationPermissionLauncher(
        onGranted = {
            homeViewModel.onIntent(
                HomeContract.Intent.NotificationPermissionResult(
                    granted = true
                )
            )
        },
        onDenied = {
            homeViewModel.onIntent(
                HomeContract.Intent.NotificationPermissionResult(
                    granted = false
                )
            )
        }
    )

    val exactAlarmLauncher = rememberExactAlarmPermissionLauncher(
        onResult = {
            homeViewModel.onIntent(
                HomeContract.Intent.RecheckSystemPermissions) }
    )

    LaunchedEffect(Unit) {
        homeViewModel.permissionEffect.collect { effect ->
            when (effect) {
                is LocationPermissionEffect.RequestPermission -> {
                    locationPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }

                is LocationPermissionEffect.PermissionResolved -> {
                    // no-op here — the ViewModel now reacts to this itself
                }

                is LocationPermissionEffect.NavigateToAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts(
                            "package",
                            context.packageName,
                            null
                        )
                    }
                    context.startActivity(intent)
                }

                is LocationPermissionEffect.NavigateToLocationSettings -> {
                    context.startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is Effect.RequestExactAlarmPermission -> {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    exactAlarmLauncher.launch(intent)
                }

                is Effect.RequestNotificationPermission -> {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }

                is Effect.ShowError -> {
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
                val permission = state.locationPermission
                if (!permission.required || permission.permanentlyDenied || permission.servicesDisabled) {
                    homeViewModel.onIntent(HomeContract.Intent.Retry)
                }
                homeViewModel.onIntent(HomeContract.Intent.RecheckSystemPermissions)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(locationServicesEnabled) {
        if (locationServicesEnabled && state.locationPermission.servicesDisabled) {
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