package com.mhq.salati.presentation.qibla

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.presentation.common.location.HandleLocationPermissionEffects
import com.mhq.salati.presentation.common.location.rememberLocationPermissionLauncher

@Composable
fun QiblaContainer(
    qiblaViewModel: QiblaViewModel = hiltViewModel()
) {
    val state by qiblaViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentState by rememberUpdatedState(state)

    HandleLocationPermissionEffects(qiblaViewModel.permissionEffect)

    val permissionLauncher = rememberLocationPermissionLauncher(
        onGranted = { qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionGranted) },
        onDenied = { permanentlyDenied ->
            qiblaViewModel.onIntent(
                QiblaContract.Intent.LocationPermissionDenied(permanentlyDenied)
            )
        }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME &&
                (currentState.locationPermission.permanentlyDenied
                        || currentState.locationPermission.servicesDisabled)
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

    LaunchedEffect(state.locationPermission.required) {
        if (state.locationPermission.required) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                qiblaViewModel.onIntent(QiblaContract.Intent.LocationPermissionGranted)
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    QiblaContent(
        state = state,
        onIntent = qiblaViewModel::onIntent
    )
}