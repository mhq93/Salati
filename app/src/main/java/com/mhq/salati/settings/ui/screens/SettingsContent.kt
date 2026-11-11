package com.mhq.salati.settings.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.SelectorType
import com.mhq.salati.settings.presentation.contract.SettingsContract.Intent
import com.mhq.salati.settings.presentation.contract.SettingsContract.State
import com.mhq.salati.settings.ui.components.SettingsActionRow
import com.mhq.salati.settings.ui.components.SettingsSectionHeader
import com.mhq.salati.settings.ui.components.SettingsSelectionSheet
import com.mhq.salati.settings.ui.components.SettingsStepperRow
import com.mhq.salati.settings.ui.components.SettingsSwitchRow
import com.mhq.salati.settings.ui.displayNameRes
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.GroupCard
import com.mhq.salati.shared.ui.components.GroupDivider
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme

/** Tall screens: the settings list never stretches wider than this (big portrait tablets). */
private val SettingsPortraitMaxWidth = 560.dp

/** Wide screens (landscape, tablets, foldables, split-screen). */
private object SettingsWideLayout {
    /** A pane's rows never stretch wider than this, even when the pane itself is huge. */
    val PaneMaxWidth = 420.dp

    val PaneSpacing = 24.dp
}

/**
 * Settings tab.
 *
 * - Narrow windows: one centered, lazy list bounded by [SettingsPortraitMaxWidth].
 * - Wide windows: two balanced panes side by side (notifications & calculation on the start
 *   side, appearance, Hijri and about on the end side), each scrolling on its own.
 * - The header sits in the column flow (like Tracker), so no measured-height overlay hack is
 *   needed and it grows safely with font scale or Arabic line heights.
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

/**
 * One centered list. Each section is its own [LazyColumn] item (real laziness), and the list is
 * bounded by [SettingsPortraitMaxWidth] so rows don't stretch on tablets. Display-cutout insets
 * keep the rows clear of a notch.
 */
@Composable
private fun SettingsNarrowContent(
    state: State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
            .navigationBarsPadding()
            .padding(bottom = BottomNavBarDefaults.Height)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
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
                SectionGap()
                PrayerCalculationSection(state = state, onIntent = onIntent)
            }

            item(key = "appearance") {
                SectionGap()
                AppearanceSection(state = state, onIntent = onIntent)
            }

            item(key = "hijri") {
                SectionGap()
                HijriCalendarSection(state = state, onIntent = onIntent)
            }

            item(key = "about") {
                SectionGap()
                AboutSection(appVersion = appVersion, onIntent = onIntent)
            }
        }
    }
}

/**
 * Two panes side by side, balanced by section count.
 *
 * Both panes scroll when the window is too short (small landscape, large font scale, Arabic):
 * `fillMaxHeight().verticalScroll()` keeps the content centered while it fits, exactly like the
 * Tracker panes. Display-cutout insets keep both panes clear of a notch in landscape.
 */
@Composable
private fun SettingsWideContent(
    state: State,
    appVersion: String,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(SettingsWideLayout.PaneSpacing),
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = BottomNavBarDefaults.Height)
    ) {
        SettingsPane(modifier = Modifier.weight(1f)) {
            NotificationsSection(state = state, onIntent = onIntent)
            SectionGap()
            PrayerCalculationSection(state = state, onIntent = onIntent)
        }

        SettingsPane(modifier = Modifier.weight(1f)) {
            AppearanceSection(state = state, onIntent = onIntent)
            SectionGap()
            HijriCalendarSection(state = state, onIntent = onIntent)
            SectionGap()
            AboutSection(appVersion = appVersion, onIntent = onIntent)
        }
    }
}

/**
 * One scrollable, horizontally bounded pane. The inner column is capped at
 * [SettingsWideLayout.PaneMaxWidth] and centered, so rows keep a sane width on tablets.
 */
@Composable
private fun SettingsPane(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = SettingsWideLayout.PaneMaxWidth)
                .fillMaxWidth(),
            content = content
        )
    }
}

@Composable
private fun SectionGap() {
    Spacer(Modifier.height(28.dp))
}

@Composable
private fun NotificationsSection(state: State, onIntent: (Intent) -> Unit) {
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

@Composable
private fun PrayerCalculationSection(state: State, onIntent: (Intent) -> Unit) {
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

@Composable
private fun AppearanceSection(state: State, onIntent: (Intent) -> Unit) {
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

@Composable
private fun HijriCalendarSection(state: State, onIntent: (Intent) -> Unit) {
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

@Composable
private fun AboutSection(appVersion: String, onIntent: (Intent) -> Unit) {
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