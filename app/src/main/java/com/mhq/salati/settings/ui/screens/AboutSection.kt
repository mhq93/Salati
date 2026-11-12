package com.mhq.salati.settings.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.ui.components.SettingsActionRow
import com.mhq.salati.settings.ui.components.SettingsSectionHeader
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.GroupDivider

@Composable
fun AboutSection(appVersion: String, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.about))
    GroupCard {
        SettingsActionRow(
            icon = Icons.Default.StarRate,
            title = stringResource(R.string.rate_salati),
            onClick = { onIntent(Intent.RateApp) }
        )
        GroupDivider()
        SettingsActionRow(
            icon = Icons.Default.Share,
            title = stringResource(R.string.share_with_friends),
            onClick = { onIntent(Intent.ShareApp) }
        )
        GroupDivider()
        SettingsActionRow(
            icon = Icons.Default.Email,
            title = stringResource(R.string.contact_support),
            onClick = { onIntent(Intent.ContactSupport) }
        )
        GroupDivider()
        SettingsActionRow(
            icon = Icons.Default.Info,
            title = stringResource(R.string.version),
            trailingText = appVersion,
            onClick = null
        )
    }
}