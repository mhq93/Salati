package com.mhq.salati.prayertracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.State
import com.mhq.salati.prayertracker.ui.components.CalendarMonthView
import com.mhq.salati.prayertracker.ui.components.PrayerStatusList
import com.mhq.salati.prayertracker.ui.components.StreakBanner
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth

/** Tall screens: content never stretches wider than this (big portrait tablets), centered. */
private val TrackerPortraitMaxWidth = 560.dp

/** Wide screens (landscape, tablets, split-screen). */
private object TrackerWideLayout {
    /** Calendar never gets wider than this inside its half of the screen. */
    val CalendarMaxWidth = 340.dp

    /** Below this height the calendar stops shrinking and the pane scrolls instead. */
    val CalendarMinHeight = 216.dp

    /** Calendar height is capped at width x this, so cells don't become tall slivers on tablets. */
    const val CalendarMaxHeightPerWidth = 1.2f

    val PaneSpacing = 24.dp
}

@Composable
fun PrayerTrackerContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    val isWide = isWideLayout()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = BottomNavBarDefaults.Height)
        ) {
            TabHeader(
                icon = Icons.Default.CheckCircle,
                title = stringResource(R.string.tracker),
                subtitle = stringResource(R.string.track_your_five_daily_prayers),
                trailingContent = {
                    if (state.currentStreak > 0) {
                        StreakBanner(state.currentStreak)
                    }
                }
            )

            if (isWide) {
                TrackerWideContent(state = state, onIntent = onIntent)
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    CalendarMonthView(
                        selectedMonth = state.selectedMonth,
                        selectedDate = state.selectedDate,
                        today = state.today,
                        dayStatus = state.monthDayStatus,
                        onDateSelected = { onIntent(Intent.DateSelected(it)) },
                        onMonthChanged = { onIntent(Intent.MonthChanged(it)) },
                        hijriOffsetDays = state.hijriDateOffset,
                        modifier = Modifier
                            .widthIn(max = TrackerPortraitMaxWidth)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    PrayerStatusList(
                        duePrayers = state.duePrayers,
                        selectedDate = state.selectedDate,
                        today = state.today,
                        records = state.selectedDateRecords,
                        onPrayerTapped = { onIntent(Intent.PrayerSelected(it)) },
                        modifier = Modifier
                            .widthIn(max = TrackerPortraitMaxWidth)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * Two panes side by side: calendar (left) and prayer checklist (right).
 *
 * - Both panes scroll when the screen is too short (small landscape, large font scale, Arabic).
 *   `fillMaxHeight().verticalScroll()` keeps the content centered while it fits.
 * - The calendar is given an explicit, bounded height (needed for its weighted rows); it is the
 *   available height, clamped between [TrackerWideLayout.CalendarMinHeight] and width x aspect cap.
 * - Display-cutout insets keep both panes clear of a notch in landscape.
 */
@Composable
private fun TrackerWideContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(TrackerWideLayout.PaneSpacing),
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
            .padding(horizontal = 24.dp, vertical = 4.dp)
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            val calendarWidth: Dp = maxWidth.coerceAtMost(TrackerWideLayout.CalendarMaxWidth)
            val calendarHeight: Dp = maxHeight
                .coerceAtMost(calendarWidth * TrackerWideLayout.CalendarMaxHeightPerWidth)
                .coerceAtLeast(TrackerWideLayout.CalendarMinHeight)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CalendarMonthView(
                    selectedMonth = state.selectedMonth,
                    selectedDate = state.selectedDate,
                    today = state.today,
                    dayStatus = state.monthDayStatus,
                    onDateSelected = { onIntent(Intent.DateSelected(it)) },
                    onMonthChanged = { onIntent(Intent.MonthChanged(it)) },
                    hijriOffsetDays = state.hijriDateOffset,
                    modifier = Modifier
                        .width(calendarWidth)
                        .height(calendarHeight)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            PrayerStatusList(
                duePrayers = state.duePrayers,
                selectedDate = state.selectedDate,
                today = state.today,
                records = state.selectedDateRecords,
                onPrayerTapped = { onIntent(Intent.PrayerSelected(it)) }
            )
        }
    }
}

@Preview(name = "Portrait", showSystemUi = true)
@Preview(name = "Landscape", device = "spec:width=800dp,height=400dp,dpi=440", showSystemUi = true)
@Preview(name = "Small landscape", device = "spec:width=640dp,height=320dp,dpi=420", showSystemUi = true)
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240", showSystemUi = true)
@Composable
private fun PrayerTrackerContentPreview() {
    SalatiTheme {
        PrayerTrackerContent(
            state = State(
                today = LocalDate.of(2026, 10, 8),
                selectedMonth = YearMonth.of(2026, 10),
                selectedDate = LocalDate.of(2026, 10, 8)
            ),
            onIntent = {}
        )
    }
}