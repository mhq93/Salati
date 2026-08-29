package com.mhq.salati.settings.presentation.screens

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.settings.presentation.contract.SettingsContract.Effect
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
            }
        }
    }

    SettingsContent(
        state = state,
        onIntent = settingsViewModel::onIntent,
        modifier = Modifier.fillMaxSize()
    )
}