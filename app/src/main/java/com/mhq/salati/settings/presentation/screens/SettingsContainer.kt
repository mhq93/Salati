package com.mhq.salati.settings.presentation.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.settings.presentation.contract.SettingsContract.Effect
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.viewmodel.SettingsViewModel
import com.mhq.salati.shared.presentation.components.LocalSnackbarHostState
import com.mhq.salati.shared.presentation.components.asString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.content.Intent as AndroidIntent

@Composable
fun SettingsContainer(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by settingsViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        settingsViewModel.onIntent(Intent.NotificationPermissionResult(granted))
    }

    // Catches the case where the user denies here, later flips it on from
    // system Settings, and comes back — recompose the real permission state
    // rather than trusting whatever we last knew.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                settingsViewModel.onIntent(Intent.RecheckNotificationPermission)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(Unit) {
        settingsViewModel.effect.collect { effect ->
            when (effect) {
                is Effect.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(effect.message.asString(context))
                    }
                }

                Effect.LaunchShareSheet -> {
                    val shareIntent = AndroidIntent(AndroidIntent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            AndroidIntent.EXTRA_TEXT,
                            "Check out Salati — prayer times & Qibla direction: " +
                                    "https://play.google.com/store/apps/details?id=${context.packageName}"
                        )
                    }
                    context.startActivity(
                        AndroidIntent.createChooser(
                            shareIntent,
                            "Share Salati"
                        )
                    )
                }

                Effect.OpenPlayStoreListing -> {
                    val uri = "market://details?id=${context.packageName}".toUri()
                    runCatching {
                        context.startActivity(
                            AndroidIntent(
                                AndroidIntent.ACTION_VIEW,
                                uri
                            )
                        )
                    }
                        .onFailure {
                            val webUri = "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
                            context.startActivity(
                                AndroidIntent(AndroidIntent.ACTION_VIEW, webUri)
                            )
                        }
                }

                Effect.OpenEmailClient -> {
                    val emailIntent = AndroidIntent(AndroidIntent.ACTION_SENDTO).apply {
                        data = "mailto:".toUri()
                        putExtra(
                            AndroidIntent.EXTRA_EMAIL,
                            arrayOf(
                                "support@salati-app.com"
                            )
                        )
                        putExtra(
                            AndroidIntent.EXTRA_SUBJECT,
                            "Salati App Feedback"
                        )
                    }
                    context.startActivity(emailIntent)
                }

                is Effect.LanguageChangedRestartRequired -> {
                    val localeList = LocaleListCompat.forLanguageTags(effect.languageCode)
                    AppCompatDelegate.setApplicationLocales(localeList)
                }

                Effect.RequestNotificationPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        // Pre-33: no runtime permission exists, so PermissionChecker
                        // should already report `true` here — nothing to request.
                        settingsViewModel.onIntent(Intent.NotificationPermissionResult(true))
                    }
                }
            }
        }
    }

    SettingsContent(
        state = state,
        onIntent = settingsViewModel::onIntent,
        modifier = Modifier.fillMaxSize()
    )
}