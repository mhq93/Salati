package com.mhq.salati.settings.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.contract.SettingsContract.State
import com.mhq.salati.settings.ui.components.SettingsSelectionSheet
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * Settings tab.
 *
 * - Narrow windows: one centered, lazy list bounded by [SettingsPortraitMaxWidth].
 * - Wide windows: two balanced panes side by side (notifications & calculation on the start
 *   side, appearance, Hijri and about on the end side), each scrolling on its own.
 * - The header sits in the column flow (like Tracker), no measured-height overlay hack is needed,
 *   and it grows safely with font scale or Arabic line heights.
 */
@Composable
fun SettingsContent(
    state: State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabHeader(
                icon = Icons.Default.Settings,
                title = stringResource(R.string.settings),
                subtitle = stringResource(R.string.customize_your_app_experience)
            )

            if (isWideLayout()) {
                SettingsWideContent(
                    state = state,
                    appVersion = appVersion,
                    onIntent = onIntent,
                    modifier = Modifier.weight(1f)
                )
            } else {
                SettingsNarrowContent(
                    state = state,
                    appVersion = appVersion,
                    onIntent = onIntent,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        state.activeSelector?.let { selector ->
            SettingsSelectionSheet(
                state = state,
                selector = selector,
                onSelect = { intent ->
                    onIntent(intent)
                    onIntent(Intent.CloseSelector)
                },
                onDismiss = { onIntent(Intent.CloseSelector) }
            )
        }
    }
}

@Preview(name = "Portrait", showSystemUi = true)
@Preview(name = "Landscape", device = "spec:width=800dp,height=400dp,dpi=440", showSystemUi = true)
@Preview(name = "Small landscape", device = "spec:width=640dp,height=320dp,dpi=420", showSystemUi = true)
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240", showSystemUi = true)
@Preview(name = "Arabic Large Font", locale = "ar", fontScale = 1.5f, showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsContentPreview() {
    SalatiTheme {
        SettingsContent(
            state = State(
                isLoading = false,
                notificationsEnabled = true,
                hasNotificationPermission = true,
                hijriDateOffset = 1
            ),
            appVersion = "1.0.4-release",
            onIntent = {}
        )
    }
}