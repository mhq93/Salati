package com.mhq.salati.presentation.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeContainer(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context as? Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME &&
                (state.locationPermissionPermanentlyDenied || state.locationServicesDisabled)
            ) {
                viewModel.onIntent(HomeContract.Intent.Retry)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val onIntentWrapped: (HomeContract.Intent) -> Unit = { intent ->
        when (intent) {
            is HomeContract.Intent.AccessAppSettings -> {
                val settingsIntent = android.content.Intent(
                    android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    android.net.Uri.fromParts("package", context.packageName, null)
                )
                context.startActivity(settingsIntent)
            }
            is HomeContract.Intent.AccessDeviceLocationSettings -> {
                val locationSettingsIntent = android.content.Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                context.startActivity(locationSettingsIntent)
            }
            else -> viewModel.onIntent(intent)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onIntent(
                HomeContract.Intent.LocationPermissionGranted
            )
        } else {
            val canAskAgain = activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } ?: true
            viewModel.onIntent(
                HomeContract.Intent.LocationPermissionDenied(
                    permanentlyDenied = !canAskAgain
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeContract.Intent.LoadPrayerTimes)
    }

    LaunchedEffect(state.locationPermissionRequired) {
        if (state.locationPermissionRequired) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                viewModel.onIntent(HomeContract.Intent.LocationPermissionGranted)
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    HomeContent(
        state = state,
        onIntent = onIntentWrapped
    )
}