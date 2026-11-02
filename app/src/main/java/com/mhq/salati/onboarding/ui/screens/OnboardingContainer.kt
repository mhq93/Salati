package com.mhq.salati.onboarding.ui.screens

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.connectivity.ui.components.rememberNetworkConnected
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.viewmodel.OnboardingViewModel
import com.mhq.salati.permissions.ui.rememberLocationPermissionLauncher
import com.mhq.salati.permissions.ui.rememberLocationServicesEnabled
import com.mhq.salati.permissions.ui.rememberNotificationPermissionLauncher
import com.mhq.salati.shared.ui.asString

@Composable
fun OnboardingContainer(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val context = LocalContext.current
    val state by onboardingViewModel.state.collectAsStateWithLifecycle()
    val isInternetConnected by rememberNetworkConnected()
    val areLocationServicesEnabled by rememberLocationServicesEnabled()

    val locationPermissionLauncher = rememberLocationPermissionLauncher(
        onGranted = {
            onboardingViewModel.onIntent(
                OnboardingContract.Intent.LocationPermissionResult(granted = true, permanentlyDenied = false)
            )
        },
        onDenied = { permanentlyDenied ->
            onboardingViewModel.onIntent(
                OnboardingContract.Intent.LocationPermissionResult(
                    granted = false,
                    permanentlyDenied = permanentlyDenied
                )
            )
        }
    )

    val notificationPermissionLauncher = rememberNotificationPermissionLauncher(
        onGranted = {
            onboardingViewModel.onIntent(OnboardingContract.Intent.NotificationPermissionResult(granted = true))
        },
        onDenied = { permanentlyDenied ->
            onboardingViewModel.onIntent(
                OnboardingContract.Intent.NotificationPermissionResult(
                    granted = false,
                    permanentlyDenied = permanentlyDenied
                )
            )
        }
    )

    LaunchedEffect(Unit) {
        onboardingViewModel.effect.collect { effect ->
            when (effect) {
                is OnboardingContract.Effect.OpenInternetSettings -> {
                    val panel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                    } else {
                        Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    }
                    context.startActivitySafely(panel, fallback = Intent(Settings.ACTION_WIRELESS_SETTINGS))
                }
                is OnboardingContract.Effect.OpenLocationSettings ->
                    context.startActivitySafely(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                is OnboardingContract.Effect.OpenAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivitySafely(intent)
                }
                is OnboardingContract.Effect.RequestLocationPermission ->
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                is OnboardingContract.Effect.RequestNotificationPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        onboardingViewModel.onIntent(
                            OnboardingContract.Intent.NotificationPermissionResult(granted = true)
                        )
                    }
                }
                is OnboardingContract.Effect.RequestExactAlarmPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivitySafely(intent)
                    }
                }
                is OnboardingContract.Effect.ApplyLanguage ->
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(effect.languageCode)
                    )
                is OnboardingContract.Effect.ShowError ->
                    Toast.makeText(context, effect.message.asString(context), Toast.LENGTH_SHORT).show()
                is OnboardingContract.Effect.NavigateToHome -> onFinished()
            }
        }
    }

    LaunchedEffect(isInternetConnected) {
        onboardingViewModel.onIntent(OnboardingContract.Intent.InternetStatusChanged(isInternetConnected))
    }

    LaunchedEffect(areLocationServicesEnabled) {
        onboardingViewModel.onIntent(
            OnboardingContract.Intent.LocationServicesStatusChanged(areLocationServicesEnabled)
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onboardingViewModel.onIntent(OnboardingContract.Intent.ScreenResumed)
    }

    OnboardingContent(
        state = state,
        onIntent = onboardingViewModel::onIntent
    )
}

private fun Context.startActivitySafely(intent: Intent, fallback: Intent? = null) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        fallback?.let { runCatching { startActivity(it) } }
    }
}