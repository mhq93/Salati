package com.mhq.salati.presentation.qibla

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
import com.mhq.salati.presentation.common.location.LocationPermissionEffect
import com.mhq.salati.presentation.common.location.rememberLocationPermissionLauncher

@Composable
fun QiblaContainer(
    qiblaViewModel: QiblaViewModel = hiltViewModel()
) {
    val state by qiblaViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionLauncher = rememberLocationPermissionLauncher(
        onGranted = { qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionGranted) },
        onDenied = { permanentlyDenied ->
            qiblaViewModel.onIntent(
                QiblaContract.Intent.LocationPermissionDenied(permanentlyDenied)
            )
        }
    )

    // Reacts to one-shot decisions made by the ViewModel — never decides anything itself
    LaunchedEffect(Unit) {
        qiblaViewModel.permissionEffect.collect { effect ->
            when (effect) {
                is LocationPermissionEffect.RequestPermission -> {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                is LocationPermissionEffect.PermissionResolved -> {
                    // no-op here — only Home needs this to sequence the notification prompt
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
                qiblaViewModel.onIntent(QiblaContract.Intent.Retry)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(Unit) {
        qiblaViewModel.onIntent(QiblaContract.Intent.LoadQibla)
    }

    QiblaContent(
        state = state,
        onIntent = qiblaViewModel::onIntent
    )
}