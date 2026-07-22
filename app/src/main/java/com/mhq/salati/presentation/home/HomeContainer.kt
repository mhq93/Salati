package com.mhq.salati.presentation.home

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.presentation.common.alarms.rememberExactAlarmPermissionLauncher
import com.mhq.salati.presentation.common.location.HandleLocationPermissionEffects
import com.mhq.salati.presentation.common.location.rememberGpsEnabled
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
    var locationRequestResolved by remember { mutableStateOf(false) }
    val gpsEnabled by rememberGpsEnabled()

    //Handling location permissions...
    HandleLocationPermissionEffects(homeViewModel.permissionEffect)

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
        onGranted = {
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
            locationRequestResolved = true
        },
        onDenied = { permanentlyDenied ->
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionDenied(permanentlyDenied))
            locationRequestResolved = true
        }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                when {
                    currentState.locationPermission.servicesDisabled -> {
                        homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
                    }
                    currentState.locationPermission.permanentlyDenied -> {
                        val hasPermission = ContextCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                        if (hasPermission) {
                            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
                        }
                        // else: still denied, do nothing, screen stays as-is
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(state.locationPermission.required) {
        if (state.locationPermission.required) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
                locationRequestResolved = true
            } else {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    LaunchedEffect(gpsEnabled) {
        if (gpsEnabled && state.locationPermission.servicesDisabled) {
            homeViewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
        }
    }

    //Handling alarms...
    val exactAlarmLauncher = rememberExactAlarmPermissionLauncher(
        onResult = { /* user returned from settings; re-check on next schedule attempt */ }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(AlarmManager::class.java)
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.fromParts(
                        "package",
                        context.packageName,
                        null
                    )
                }
                exactAlarmLauncher.launch(intent)
            }
        }
    }

    //Handling notifications...
    val notificationPermissionLauncher = rememberNotificationPermissionLauncher(
        onGranted = { /* proceed, e.g. mark alarms enabled */ },
        onDenied = { /* show rationale or leave notifications off */ }
    )

    LaunchedEffect(locationRequestResolved) {
        if (locationRequestResolved && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
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