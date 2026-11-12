package com.mhq.salati.settings.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.ui.components.SettingsSectionHeader
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.GroupDivider

@Composable
fun PrayerCalculationSection(state: SettingsContract.State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.prayer_calculation))
    GroupCard {
        ActionRow(
            icon = Icons.Default.Explore,
            title = stringResource(R.string.calculation_method),
            valueLabel = stringResource(state.calculationMethod.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.CalculationMethod)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.School,
            title = stringResource(R.string.asr_madhab),
            valueLabel = stringResource(state.madhab.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.Madhab)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.Schedule,
            title = stringResource(R.string.time_format),
            valueLabel = stringResource(state.timeFormat.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.TimeFormat)) }
        )
    }
}