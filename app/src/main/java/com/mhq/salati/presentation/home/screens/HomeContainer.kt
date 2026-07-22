package com.mhq.salati.presentation.home.screens

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.presentation.common.alarms.rememberExactAlarmPermissionLauncher
import com.mhq.salati.presentation.common.location.LocationPermissionEffect
import com.mhq.salati.presentation.common.location.rememberGpsEnabled
import com.mhq.salati.presentation.common.location.rememberLocationPermissionLauncher
import com.mhq.salati.presentation.common.notifications.rememberNotificationPermissionLauncher
import com.mhq.salati.presentation.home.HomeContract
import com.mhq.salati.presentation.home.HomeViewModel

@Composable
fun HomeContainer(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val gpsEnabled by rememberGpsEnabled()
    var locationFlowResolved by remember { mutableStateOf(false) }

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

    // Reacts to one-shot decisions made by the ViewModel — never decides anything itself
    LaunchedEffect(Unit) {
        homeViewModel.permissionEffect.collect { effect ->
            when (effect) {
                is LocationPermissionEffect.RequestPermission -> {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                is LocationPermissionEffect.PermissionResolved -> {
                    locationFlowResolved = true
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

    //Handling alarms...
    val exactAlarmLauncher = rememberExactAlarmPermissionLauncher(
        onResult = { /* user returned from settings; re-check on next schedule attempt */ }
    )

    LaunchedEffect(locationFlowResolved) {
        if (locationFlowResolved && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(AlarmManager::class.java)
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                exactAlarmLauncher.launch(intent)
            }
        }
    }

    //Handling notifications... only once the location permission decision is fully settled
    LaunchedEffect(locationFlowResolved) {
        if (locationFlowResolved && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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