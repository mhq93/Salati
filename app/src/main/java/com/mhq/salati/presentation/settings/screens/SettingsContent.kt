package com.mhq.salati.presentation.settings.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhq.salati.presentation.settings.SettingsContract.Intent
import com.mhq.salati.presentation.settings.SettingsContract.State
import com.mhq.salati.presentation.settings.components.SelectorType
import com.mhq.salati.presentation.settings.components.SettingsActionRow
import com.mhq.salati.presentation.settings.components.SettingsCard
import com.mhq.salati.presentation.settings.components.SettingsDivider
import com.mhq.salati.presentation.settings.components.SettingsHeader
import com.mhq.salati.presentation.settings.components.SettingsSectionHeader
import com.mhq.salati.presentation.settings.components.SettingsSelectionSheet
import com.mhq.salati.presentation.settings.components.SettingsSelectorRow
import com.mhq.salati.presentation.settings.components.SettingsStepperRow
import com.mhq.salati.presentation.settings.components.SettingsSwitchRow
import com.mhq.salati.presentation.theme.SheetBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    state: State,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(SheetBackground)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 140.dp + 32.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item { SettingsHeader() }

            item {
                Column(
                    Modifier.padding(horizontal = 20.dp)
                ) {
                    Spacer(
                        Modifier.height(24.dp)
                    )

                    SettingsSectionHeader(
                        "Notifications"
                    )

                    SettingsCard {
                        SettingsSwitchRow(
                            icon = Icons.Default.NotificationsActive,
                            title = "Prayer Notifications",
                            subtitle = "Get alerted at each prayer time",
                            isChecked = state.notificationsEnabled,
                            onCheckedChange = { onIntent(Intent.ToggleNotifications(it)) }
                        )

                        SettingsDivider()

                        SettingsSelectorRow(
                            icon = Icons.Default.MusicNote,
                            title = "Adhan Sound",
                            valueLabel = state.adhanSound.displayName,
                            isEnabled = state.notificationsEnabled,
                            onClick = { onIntent(Intent.OpenSelector(SelectorType.AdhanSound)) }
                        )
                    }

                    Spacer(
                        Modifier.height(28.dp)
                    )

                    SettingsSectionHeader(
                        "Prayer Calculation"
                    )

                    SettingsCard {
                        SettingsSelectorRow(
                            icon = Icons.Default.Explore,
                            title = "Calculation Method",
                            valueLabel = state.calculationMethod.displayName,
                            onClick = { onIntent(Intent.OpenSelector(SelectorType.CalculationMethod)) }
                        )

                        SettingsDivider()

                        SettingsSelectorRow(
                            icon = Icons.Default.School,
                            title = "Asr Madhab",
                            valueLabel = state.madhab.displayName,
                            onClick = { onIntent(Intent.OpenSelector(SelectorType.Madhab)) }
                        )
                    }

                    Spacer(
                        Modifier.height(28.dp)
                    )

                    SettingsSectionHeader(
                        "Appearance"
                    )

                    SettingsCard {
                        SettingsSelectorRow(
                            icon = Icons.Default.DarkMode,
                            title = "Theme",
                            valueLabel = state.themeMode.displayName,
                            onClick = { onIntent(Intent.OpenSelector(SelectorType.Theme)) }
                        )

                        SettingsDivider()

                        SettingsSelectorRow(
                            icon = Icons.Default.Language,
                            title = "Language",
                            valueLabel = state.language.displayName,
                            onClick = { onIntent(Intent.OpenSelector(SelectorType.Language))}
                        )
                    }

                    Spacer(
                        Modifier.height(28.dp)
                    )

                    SettingsSectionHeader(
                        "Hijri Calendar"
                    )

                    SettingsCard {
                        SettingsStepperRow(
                            icon = Icons.Default.CalendarMonth,
                            title = "Date Adjustment",
                            subtitle = "Correct for local moon sighting",
                            value = state.hijriDateOffset,
                            onIncrement = { onIntent(Intent.IncrementHijriOffset) },
                            onDecrement = { onIntent(Intent.DecrementHijriOffset) }
                        )
                    }

                    Spacer(
                        Modifier.height(28.dp)
                    )

                    SettingsSectionHeader(
                        "About"
                    )

                    SettingsCard {
                        SettingsActionRow(
                            icon = Icons.Default.StarRate,
                            title = "Rate Salati",
                            onClick = { onIntent(Intent.RateApp) }
                        )

                        SettingsDivider()

                        SettingsActionRow(
                            icon = Icons.Default.Share,
                            title = "Share with Friends",
                            onClick = { onIntent(Intent.ShareApp) }
                        )

                        SettingsDivider()

                        SettingsActionRow(
                            icon = Icons.Default.Email,
                            title = "Contact Support",
                            onClick = { onIntent(Intent.ContactSupport) }
                        )

                        SettingsDivider()

                        SettingsActionRow(
                            icon = Icons.Default.Info,
                            title = "Version",
                            trailingText = state.appVersion,
                            onClick = null
                        )
                    }
                }
            }
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