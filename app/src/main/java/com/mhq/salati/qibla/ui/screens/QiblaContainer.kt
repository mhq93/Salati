package com.mhq.salati.qibla.ui.screens

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
import com.mhq.salati.location.ui.LocationPrerequisitesObserver
import com.mhq.salati.location.ui.rememberLocationPrerequisitesActions
import com.mhq.salati.permissions.ui.shouldShowLocationRationale
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.viewmodel.QiblaViewModel
import com.mhq.salati.qibla.ui.components.CompassCalibrationOverlay
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.components.LocalSnackbarHostState
import kotlinx.coroutines.launch

@Composable
fun QiblaContainer(
    qiblaViewModel: QiblaViewModel = hiltViewModel(),
    onNavigateToLocationPicker: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by qiblaViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    val locationActions = rememberLocationPrerequisitesActions(
        onPermissionGranted = {
            qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionGranted)
        },
        onPermissionDenied = { showRationale ->
            qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionDenied(showRationale))
        }
    )

    LaunchedEffect(Unit) {
        qiblaViewModel.effect.collect { effect ->
            when (effect) {
                is QiblaContract.Effect.RequestLocationPermission -> locationActions.requestPermission()
                is QiblaContract.Effect.NavigateToAppSettings -> locationActions.openAppSettings()
                is QiblaContract.Effect.NavigateToLocationSettings -> locationActions.openLocationSettings()
                is QiblaContract.Effect.NavigateToInternetSettings -> locationActions.openInternetSettings()
                is QiblaContract.Effect.NavigateToLocationPicker -> onNavigateToLocationPicker()
                is QiblaContract.Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }
            }
        }
    }

    // The destination's own lifecycle is RESUMED only while this tab is visible and the app is in
    // the foreground, so it alone decides when the compass runs.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME ->
                    qiblaViewModel.onIntent(
                        QiblaContract.Intent.ScreenResumed(context.shouldShowLocationRationale())
                    )

                Lifecycle.Event.ON_PAUSE ->
                    qiblaViewModel.onIntent(QiblaContract.Intent.ScreenPaused)

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Safety net: if this composable is disposed outright (tab switch, since NavHost only composes
    // the current destination) the compass still gets stopped.
    DisposableEffect(Unit) {
        onDispose {
            qiblaViewModel.onIntent(QiblaContract.Intent.ScreenPaused)
        }
    }

    LocationPrerequisitesObserver(
        onServicesChanged = { enabled ->
            qiblaViewModel.onIntent(QiblaContract.Intent.LocationServicesToggled(enabled))
        },
        onConnectivityChanged = { connected ->
            qiblaViewModel.onIntent(QiblaContract.Intent.ConnectivityChanged(connected))
        }
    )

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