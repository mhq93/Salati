package com.mhq.salati.settings.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.ui.screens.SettingsLayout.SettingsPortraitMaxWidth
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults

/**
 * One centered list. Each section is its own [LazyColumn] item (real laziness),
 * and the list is bounded by [SettingsPortraitMaxWidth] so rows don't stretch on tablets.
 * Display-cutout insets keep the rows clear of a notch.
 */
@Composable
fun SettingsNarrowContent(
    state: SettingsContract.State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = BottomNavBarDefaults.Height)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = ScreenHorizontalPadding,
                end = ScreenHorizontalPadding,
                top = 24.dp,
                bottom = 40.dp
            ),
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = SettingsPortraitMaxWidth)
        ) {
            item(key = "notifications") {
                NotificationsSection(state = state, onIntent = onIntent)
            }

            item(key = "calculation") {
                Spacer(Modifier.height(28.dp))
                PrayerCalculationSection(state = state, onIntent = onIntent)
            }

            item(key = "appearance") {
                Spacer(Modifier.height(28.dp))
                AppearanceSection(state = state, onIntent = onIntent)
            }

            item(key = "hijri") {
                Spacer(Modifier.height(28.dp))
                HijriCalendarSection(state = state, onIntent = onIntent)
            }

            item(key = "about") {
                Spacer(Modifier.height(28.dp))
                AboutSection(appVersion = appVersion, onIntent = onIntent)
            }
        }
    }
}