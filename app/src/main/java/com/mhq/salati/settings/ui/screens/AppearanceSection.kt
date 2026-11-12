package com.mhq.salati.settings.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
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
fun AppearanceSection(state: SettingsContract.State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.appearance))
    GroupCard {
        ActionRow(
            icon = Icons.Default.DarkMode,
            title = stringResource(R.string.theme),
            valueLabel = stringResource(state.themeMode.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.Theme)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.Language,
            title = stringResource(R.string.language),
            valueLabel = stringResource(state.language.displayNameRes),
            onClick = { onIntent(Intent.OpenSelector(SelectorType.Language)) }
        )
    }
}