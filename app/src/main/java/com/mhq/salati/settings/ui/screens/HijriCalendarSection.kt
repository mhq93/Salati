package com.mhq.salati.settings.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.ui.components.SettingsSectionHeader
import com.mhq.salati.settings.ui.components.SettingsStepperRow
import com.mhq.salati.shared.ui.components.GroupCard

@Composable
fun HijriCalendarSection(state: SettingsContract.State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.hijri_calendar))
    GroupCard {
        SettingsStepperRow(
            icon = Icons.Default.CalendarMonth,
            title = stringResource(R.string.date_adjustment),
            subtitle = stringResource(R.string.correct_for_local_moon_sighting),
            value = state.hijriDateOffset,
            onIncrement = { onIntent(Intent.IncrementHijriOffset) },
            onDecrement = { onIntent(Intent.DecrementHijriOffset) }
        )
    }
}