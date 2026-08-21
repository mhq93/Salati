package com.mhq.salati.qibla.presentation.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.permissions.location.rememberLocationPermissionLauncher
import com.mhq.salati.permissions.location.rememberLocationServicesEnabled
import com.mhq.salati.qibla.presentation.components.CompassCalibrationOverlay
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.viewmodel.QiblaViewModel
import com.mhq.salati.shared.presentation.components.LocalSnackbarHostState
import com.mhq.salati.shared.presentation.components.asString
import kotlinx.coroutines.launch

@Composable
fun QiblaContainer(
    onNavigateToLocationPicker: () -> Unit,
    qiblaViewModel: QiblaViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by qiblaViewModel.state.collectAsStateWithLifecycle()
    val locationServicesEnabled by rememberLocationServicesEnabled()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
        onGranted = { qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionGranted) },
        onDenied = { permanentlyDenied ->
            qiblaViewModel.onIntent(
                QiblaContract.Intent.LocationPermissionDenied(permanentlyDenied)
            )
        }
    )

    // FIX: Single effect stream — no more permissionEffect
    LaunchedEffect(Unit) {
        qiblaViewModel.effect.collect { effect ->
            when (effect) {
                is QiblaContract.Effect.RequestLocationPermission -> {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                is QiblaContract.Effect.NavigateToAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
                is QiblaContract.Effect.NavigateToLocationSettings -> {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                is QiblaContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
                is QiblaContract.Effect.NavigateToLocationPicker -> onNavigateToLocationPicker()
            }
        }
    }

    // FIX: Dumb Container — just reports events, no conditional logic
    // Also: consolidated two redundant DisposableEffect blocks into one
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                qiblaViewModel.onIntent(QiblaContract.Intent.ScreenResumed)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(locationServicesEnabled) {
        qiblaViewModel.onIntent(
            QiblaContract.Intent.LocationServicesToggled(enabled = locationServicesEnabled)
        )
    }

    LaunchedEffect(Unit) {
        qiblaViewModel.onIntent(QiblaContract.Intent.LoadQibla)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        QiblaContent(
            state = state,
            onIntent = qiblaViewModel::onIntent
        )

        if (state.isCalibrationGuideVisible) {
            BackHandler {
                qiblaViewModel.onIntent(QiblaContract.Intent.DismissCalibrationGuide)
            }
            CompassCalibrationOverlay(
                accuracy = state.compassAccuracy,
                onDismiss = { qiblaViewModel.onIntent(QiblaContract.Intent.DismissCalibrationGuide) }
            )
        }
    }
}