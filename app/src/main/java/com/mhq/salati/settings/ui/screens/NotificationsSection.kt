package com.mhq.salati.settings.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.ui.components.SettingsSectionHeader
import com.mhq.salati.settings.ui.components.SettingsSwitchRow
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.GroupDivider

@Composable
fun NotificationsSection(state: SettingsContract.State, onIntent: (Intent) -> Unit) {
    SettingsSectionHeader(stringResource(R.string.notifications))
    GroupCard {
        SettingsSwitchRow(
            icon = Icons.Default.NotificationsActive,
            title = stringResource(R.string.prayer_notifications),
            subtitle = stringResource(R.string.get_alerted_and_notified_at_each_prayer_time),
            isChecked = state.notificationsEnabled && state.hasNotificationPermission,
            onCheckedChange = { onIntent(Intent.ToggleNotifications(it)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.MusicNote,
            title = stringResource(R.string.adhan_sound),
            valueLabel = stringResource(state.adhanSound.displayNameRes),
            isEnabled = state.notificationsEnabled,
            onClick = { onIntent(Intent.OpenSelector(SelectorType.AdhanSound)) }
        )
        GroupDivider()
        ActionRow(
            icon = Icons.Default.MusicNote,
            title = stringResource(R.string.custom_alarm_sound),
            valueLabel = stringResource(state.customAlarmSound.displayNameRes),
            isEnabled = state.notificationsEnabled,
            onClick = { onIntent(Intent.OpenSelector(SelectorType.CustomAlarmSound)) }
        )
    }
}