package com.mhq.salati.settings.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhq.salati.settings.presentation.contract.SettingsContract.Effect
import com.mhq.salati.settings.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import android.content.Intent as AndroidIntent

@Composable
fun SettingsContainer(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by settingsViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        settingsViewModel.effect.collectLatest { effect ->
            when (effect) {
                is Effect.ShowError -> {
                    // TODO: wire to the app-wide snackbar/toast mechanism once decided (same open item as Home/Qibla)
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
                            val webUri =
                                "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
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

                Effect.LanguageChangedRestartRequired -> {
                    // TODO: apply via AppCompatDelegate.setApplicationLocales() once language switching is wired app-wide
                }
            }
        }
    }

    SettingsContent(
        state = state,
        onIntent = settingsViewModel::onIntent,
        modifier = modifier.fillMaxSize()
    )
}