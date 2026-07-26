package com.mhq.salati.qibla.presentation.screens

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
import com.mhq.salati.location.LocationPermissionEffect
import com.mhq.salati.location.rememberGpsEnabled
import com.mhq.salati.location.rememberLocationPermissionLauncher
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.viewmodel.QiblaViewModel

@Composable
fun QiblaContainer(
    qiblaViewModel: QiblaViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by qiblaViewModel.state.collectAsStateWithLifecycle()
    val gpsEnabled by rememberGpsEnabled()

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
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
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }

                is LocationPermissionEffect.PermissionResolved -> {
                    // no-op here — only Home needs this to sequence the notification prompt
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

    // Reacts to one-shot decisions made by the ViewModel — never decides anything itself
    LaunchedEffect(Unit) {
        qiblaViewModel.effect.collect { effect ->
            when (effect) {
                is QiblaContract.Effect.ShowError -> {
                    // TODO: surface via snackbar/toast — same mechanism as Home
                }
                is QiblaContract.Effect.LocationPickerNotImplemented -> {
                    // TODO: navigate to location picker once built
                }
                is QiblaContract.Effect.CompassCalibrationNotImplemented -> {
                    // TODO: show calibration guidance once designed
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME &&
                (state.locationPermission.permanentlyDenied
                        || state.locationPermission.servicesDisabled)
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

    LaunchedEffect(gpsEnabled) {
        if (gpsEnabled && state.locationPermission.servicesDisabled) {
            qiblaViewModel.onIntent(QiblaContract.Intent.Retry)
        }
    }

    QiblaContent(
        state = state,
        onIntent = qiblaViewModel::onIntent
    )
}