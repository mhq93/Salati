package com.mhq.salati.settings.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.components.SettingsActionRow
import com.mhq.salati.settings.presentation.components.SettingsCard
import com.mhq.salati.settings.presentation.components.SettingsDivider
import com.mhq.salati.settings.presentation.components.SettingsSectionHeader
import com.mhq.salati.settings.presentation.components.SettingsSelectionSheet
import com.mhq.salati.settings.presentation.components.SettingsSelectorRow
import com.mhq.salati.settings.presentation.components.SettingsStepperRow
import com.mhq.salati.settings.presentation.components.SettingsSwitchRow
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.contract.SettingsContract.State
import com.mhq.salati.shared.presentation.components.BottomNavDefaults
import com.mhq.salati.shared.presentation.components.TabHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    state: State,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var headerHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // <-- Replaced SheetBackground
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = with(density) { headerHeightPx.toDp() },
                bottom = BottomNavDefaults.Height + 40.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Spacer(Modifier.height(24.dp))

                    SettingsSectionHeader(stringResource(R.string.notifications))
                    SettingsCard {
                        SettingsSwitchRow(
                            icon = Icons.Default.NotificationsActive,
                            title = stringResource(R.string.prayer_notifications),
                            subtitle = stringResource(R.string.get_alerted_and_notified_at_each_prayer_time),
                            isChecked = state.notificationsEnabled,
                            onCheckedChange = { onIntent(Intent.ToggleNotifications(it)) }
                        )
                        SettingsDivider()
                        SettingsSelectorRow(
                            icon = Icons.Default.MusicNote,
                            title = stringResource(R.string.adhan_sound),
                            valueLabel = stringResource(state.adhanSound.displayNameRes),
                            isEnabled = state.notificationsEnabled,
                            onClick = { onIntent(Intent.OpenSelector(com.mhq.salati.settings.presentation.components.SelectorType.AdhanSound)) }
                        )
                    }

                    Spacer(Modifier.height(28.dp))
                    SettingsSectionHeader(stringResource(R.string.prayer_calculation))
                    SettingsCard {
                        SettingsSelectorRow(
                            icon = Icons.Default.Explore,
                            title = stringResource(R.string.calculation_method),
                            valueLabel = stringResource(state.calculationMethod.displayNameRes),
                            onClick = { onIntent(Intent.OpenSelector(com.mhq.salati.settings.presentation.components.SelectorType.CalculationMethod)) }
                        )
                        SettingsDivider()
                        SettingsSelectorRow(
                            icon = Icons.Default.School,
                            title = stringResource(R.string.asr_madhab),
                            valueLabel = stringResource(state.madhab.displayNameRes),
                            onClick = { onIntent(Intent.OpenSelector(com.mhq.salati.settings.presentation.components.SelectorType.Madhab)) }
                        )
                    }

                    Spacer(Modifier.height(28.dp))
                    SettingsSectionHeader(stringResource(R.string.appearance))
                    SettingsCard {
                        SettingsSelectorRow(
                            icon = Icons.Default.DarkMode,
                            title = stringResource(R.string.theme),
                            valueLabel = stringResource(state.themeMode.displayNameRes),
                            onClick = { onIntent(Intent.OpenSelector(com.mhq.salati.settings.presentation.components.SelectorType.Theme)) }
                        )
                        SettingsDivider()
                        SettingsSelectorRow(
                            icon = Icons.Default.Language,
                            title = stringResource(R.string.language),
                            valueLabel = stringResource(state.language.displayNameRes),
                            onClick = { onIntent(Intent.OpenSelector(com.mhq.salati.settings.presentation.components.SelectorType.Language)) }
                        )
                    }

                    Spacer(Modifier.height(28.dp))
                    SettingsSectionHeader(stringResource(R.string.hijri_calendar))
                    SettingsCard {
                        SettingsStepperRow(
                            icon = Icons.Default.CalendarMonth,
                            title = stringResource(R.string.date_adjustment),
                            subtitle = stringResource(R.string.correct_for_local_moon_sighting),
                            value = state.hijriDateOffset,
                            onIncrement = { onIntent(Intent.IncrementHijriOffset) },
                            onDecrement = { onIntent(Intent.DecrementHijriOffset) }
                        )
                    }

                    Spacer(Modifier.height(28.dp))
                    SettingsSectionHeader(stringResource(R.string.about))
                    SettingsCard {
                        SettingsActionRow(
                            icon = Icons.Default.StarRate,
                            title = stringResource(R.string.rate_salati),
                            onClick = { onIntent(Intent.RateApp) }
                        )
                        SettingsDivider()
                        SettingsActionRow(
                            icon = Icons.Default.Share,
                            title = stringResource(R.string.share_with_friends),
                            onClick = { onIntent(Intent.ShareApp) }
                        )
                        SettingsDivider()
                        SettingsActionRow(
                            icon = Icons.Default.Email,
                            title = stringResource(R.string.contact_support),
                            onClick = { onIntent(Intent.ContactSupport) }
                        )
                        SettingsDivider()
                        SettingsActionRow(
                            icon = Icons.Default.Info,
                            title = stringResource(R.string.version),
                            trailingText = state.appVersion,
                            onClick = null
                        )
                    }
                }
            }
        }

        TabHeader(
            icon = Icons.Default.Settings,
            title = stringResource(R.string.settings),
            subtitle = stringResource(R.string.customize_your_app_experience),
            modifier = Modifier.onGloballyPositioned {
                headerHeightPx = it.size.height
            }
        )

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

//package com.mhq.salati.settings.presentation.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CalendarMonth
//import androidx.compose.material.icons.filled.DarkMode
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Explore
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material.icons.filled.Language
//import androidx.compose.material.icons.filled.MusicNote
//import androidx.compose.material.icons.filled.NotificationsActive
//import androidx.compose.material.icons.filled.School
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material.icons.filled.Share
//import androidx.compose.material.icons.filled.StarRate
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.mhq.salati.R
//import com.mhq.salati.settings.presentation.components.SelectorType
//import com.mhq.salati.settings.presentation.components.SettingsActionRow
//import com.mhq.salati.settings.presentation.components.SettingsCard
//import com.mhq.salati.settings.presentation.components.SettingsDivider
//import com.mhq.salati.settings.presentation.components.SettingsSectionHeader
//import com.mhq.salati.settings.presentation.components.SettingsSelectionSheet
//import com.mhq.salati.settings.presentation.components.SettingsSelectorRow
//import com.mhq.salati.settings.presentation.components.SettingsStepperRow
//import com.mhq.salati.settings.presentation.components.SettingsSwitchRow
//import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
//import com.mhq.salati.settings.presentation.contract.SettingsContract.State
//import com.mhq.salati.shared.presentation.components.BottomNavDefaults
//import com.mhq.salati.shared.presentation.components.TabHeader
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//import com.mhq.salati.shared.presentation.theme.SheetBackground
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SettingsContent(
//    state: State,
//    onIntent: (Intent) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val density = LocalDensity.current
//    var headerHeightPx by remember { mutableIntStateOf(0) }
//
//    Box(
//        modifier = modifier
//            .background(SheetBackground)
//    ) {
//        LazyColumn(
//            contentPadding = PaddingValues(
//                top = with(density) { headerHeightPx.toDp() },
//                bottom = BottomNavDefaults.Height + 40.dp
//            ),
//            modifier = Modifier
//                .fillMaxSize()
//                .navigationBarsPadding()
//        ) {
//            item {
//                Column(
//                    Modifier.padding(horizontal = 20.dp)
//                ) {
//                    Spacer(
//                        Modifier.height(24.dp)
//                    )
//                    SettingsSectionHeader(
//                        stringResource(R.string.notifications)
//                    )
//                    SettingsCard {
//                        SettingsSwitchRow(
//                            icon = Icons.Default.NotificationsActive,
//                            title = stringResource(R.string.prayer_notifications),
//                            subtitle = stringResource(R.string.get_alerted_and_notified_at_each_prayer_time),
//                            isChecked = state.notificationsEnabled,
//                            onCheckedChange = { onIntent(Intent.ToggleNotifications(it)) }
//                        )
//                        SettingsDivider()
//                        SettingsSelectorRow(
//                            icon = Icons.Default.MusicNote,
//                            title = stringResource(R.string.adhan_sound),
//                            valueLabel = stringResource(state.adhanSound.displayNameRes),
//                            isEnabled = state.notificationsEnabled,
//                            onClick = { onIntent(Intent.OpenSelector(SelectorType.AdhanSound)) }
//                        )
//                    }
//                    Spacer(
//                        Modifier.height(28.dp)
//                    )
//                    SettingsSectionHeader(
//                        stringResource(R.string.prayer_calculation)
//                    )
//                    SettingsCard {
//                        SettingsSelectorRow(
//                            icon = Icons.Default.Explore,
//                            title = stringResource(R.string.calculation_method),
//                            valueLabel = stringResource(state.calculationMethod.displayNameRes),
//                            onClick = { onIntent(Intent.OpenSelector(SelectorType.CalculationMethod)) }
//                        )
//                        SettingsDivider()
//                        SettingsSelectorRow(
//                            icon = Icons.Default.School,
//                            title = stringResource(R.string.asr_madhab),
//                            valueLabel = stringResource(state.madhab.displayNameRes),
//                            onClick = { onIntent(Intent.OpenSelector(SelectorType.Madhab)) }
//                        )
//                    }
//                    Spacer(
//                        Modifier.height(28.dp)
//                    )
//                    SettingsSectionHeader(
//                        stringResource(R.string.appearance)
//                    )
//                    SettingsCard {
//                        SettingsSelectorRow(
//                            icon = Icons.Default.DarkMode,
//                            title = stringResource(R.string.theme),
//                            valueLabel = stringResource(state.themeMode.displayNameRes),
//                            onClick = { onIntent(Intent.OpenSelector(SelectorType.Theme)) }
//                        )
//                        SettingsDivider()
//                        SettingsSelectorRow(
//                            icon = Icons.Default.Language,
//                            title = stringResource(R.string.language),
//                            valueLabel = stringResource(state.language.displayNameRes),
//                            onClick = { onIntent(Intent.OpenSelector(SelectorType.Language)) }
//                        )
//                    }
//                    Spacer(
//                        Modifier.height(28.dp)
//                    )
//                    SettingsSectionHeader(
//                        stringResource(R.string.hijri_calendar)
//                    )
//                    SettingsCard {
//                        SettingsStepperRow(
//                            icon = Icons.Default.CalendarMonth,
//                            title = stringResource(R.string.date_adjustment),
//                            subtitle = stringResource(R.string.correct_for_local_moon_sighting),
//                            value = state.hijriDateOffset,
//                            onIncrement = { onIntent(Intent.IncrementHijriOffset) },
//                            onDecrement = { onIntent(Intent.DecrementHijriOffset) }
//                        )
//                    }
//                    Spacer(
//                        Modifier.height(28.dp)
//                    )
//                    SettingsSectionHeader(
//                        stringResource(R.string.about)
//                    )
//                    SettingsCard {
//                        SettingsActionRow(
//                            icon = Icons.Default.StarRate,
//                            title = stringResource(R.string.rate_salati),
//                            onClick = { onIntent(Intent.RateApp) }
//                        )
//                        SettingsDivider()
//                        SettingsActionRow(
//                            icon = Icons.Default.Share,
//                            title = stringResource(R.string.share_with_friends),
//                            onClick = { onIntent(Intent.ShareApp) }
//                        )
//                        SettingsDivider()
//                        SettingsActionRow(
//                            icon = Icons.Default.Email,
//                            title = stringResource(R.string.contact_support),
//                            onClick = { onIntent(Intent.ContactSupport) }
//                        )
//                        SettingsDivider()
//                        SettingsActionRow(
//                            icon = Icons.Default.Info,
//                            title = stringResource(R.string.version),
//                            trailingText = state.appVersion,
//                            onClick = null
//                        )
//                    }
//                }
//            }
//        }
//
//        TabHeader(
//            icon = Icons.Default.Settings,
//            title = stringResource(R.string.settings),
//            subtitle = stringResource(R.string.customize_your_app_experience),
//            modifier = Modifier.onGloballyPositioned {
//                headerHeightPx = it.size.height
//            }
//        )
//
//        state.activeSelector?.let { selector ->
//            SettingsSelectionSheet(
//                state = state,
//                selector = selector,
//                onSelect = { intent ->
//                    onIntent(intent)
//                    onIntent(Intent.CloseSelector)
//                },
//                onDismiss = { onIntent(Intent.CloseSelector) }
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun SettingsContentPreview() {
//    SalatiTheme() {
//        SettingsContent(
//            state = State(),
//            onIntent = {}
//        )
//    }
//}