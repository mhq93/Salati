package com.mhq.salati.qibla.ui.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.permissions.ui.rememberLocationPermissionLauncher
import com.mhq.salati.permissions.ui.rememberLocationServicesEnabled
import com.mhq.salati.qibla.ui.components.CompassCalibrationOverlay
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.viewmodel.QiblaViewModel
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import com.mhq.salati.shared.ui.asString
import kotlinx.coroutines.launch

@Composable
fun QiblaContainer(
    qiblaViewModel: QiblaViewModel = hiltViewModel(),
    onNavigateToLocationPicker: () -> Unit,
    isActiveTab: Boolean // NEW
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by qiblaViewModel.state.collectAsStateWithLifecycle()
    val locationServicesEnabled by rememberLocationServicesEnabled()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    // CHANGED — ON_PAUSE no longer sends ScreenPaused directly; it just
    // tracks foreground state, which combines with isActiveTab below
    var isAppForegrounded by remember { mutableStateOf(true) }

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
        onGranted = { qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionGranted) },
        onDenied = { permanentlyDenied ->
            qiblaViewModel.onIntent(
                QiblaContract.Intent.LocationPermissionDenied(permanentlyDenied)
            )
        }
    )

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

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    isAppForegrounded = true
                    // CHANGED — only do the full reload (network geocode + GPS)
                    // when this tab is actually the one visible. Every other
                    // tab-switch case already re-triggers LoadQibla naturally
                    // when the composable is remounted (NavHost only composes
                    // the current destination), so this was pure waste before.
                    if (isActiveTab) {
                        qiblaViewModel.onIntent(QiblaContract.Intent.ScreenResumed)
                    }
                }
                Lifecycle.Event.ON_PAUSE -> {
                    isAppForegrounded = false
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // NEW — single source of truth: compass only runs while this tab is the
    // one visible AND the app is in the foreground. Covers tab-switch,
    // app backgrounding, and screen lock, without double-firing either event.
    val isCompassActive = isActiveTab && isAppForegrounded
    LaunchedEffect(isCompassActive) {
        if (isCompassActive) {
            qiblaViewModel.onIntent(QiblaContract.Intent.CompassResumed)
        } else {
            qiblaViewModel.onIntent(QiblaContract.Intent.ScreenPaused)
        }
    }

    // NEW — safety net: if this composable is disposed outright (tab switch,
    // since NavHost only composes the current destination) before isActiveTab
    // ever recomposes to false, this guarantees the compass still gets stopped.
    DisposableEffect(Unit) {
        onDispose {
            qiblaViewModel.onIntent(QiblaContract.Intent.ScreenPaused)
        }
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