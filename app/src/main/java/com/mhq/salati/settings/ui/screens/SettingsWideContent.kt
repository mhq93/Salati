package com.mhq.salati.settings.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.screenHorizontalMargin

/**
 * Two panes side by side, balanced by section count.
 *
 * Both panes scroll when the window is too short (small landscape, large font scale, Arabic):
 * `fillMaxHeight().verticalScroll()` keeps the content centered while it fits,
 * exactly like the Tracker panes. Display-cutout insets keep panes clear of a notch in landscape.
 */
@Composable
fun SettingsWideContent(
    state: SettingsContract.State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(SettingsLayout.PaneSpacing),
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
            )
            .navigationBarsPadding()
            .padding(
                start = screenHorizontalMargin(),
                end = screenHorizontalMargin(),
                bottom = BottomNavBarDefaults.Height
            )
    ) {
        SettingsPane(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
            NotificationsSection(state = state, onIntent = onIntent)
            Spacer(Modifier.height(28.dp))
            PrayerCalculationSection(state = state, onIntent = onIntent)
        }

        SettingsPane(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
            AppearanceSection(state = state, onIntent = onIntent)
            Spacer(Modifier.height(28.dp))
            HijriCalendarSection(state = state, onIntent = onIntent)
            Spacer(Modifier.height(28.dp))
            AboutSection(appVersion = appVersion, onIntent = onIntent)
        }
    }
}