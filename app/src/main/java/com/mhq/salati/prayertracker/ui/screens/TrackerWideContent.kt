package com.mhq.salati.prayertracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
import com.mhq.salati.prayertracker.ui.components.CalendarMonthView
import com.mhq.salati.prayertracker.ui.components.PrayerStatusList
import com.mhq.salati.shared.ui.screenHorizontalMargin

/**
 * Two panes side by side: calendar (left) and prayer checklist (right).
 *
 * - Both panes scroll when the screen is too short (small landscape, large font scale, Arabic).
 *   `fillMaxHeight().verticalScroll()` keeps the content centered while it fits.
 * - The calendar is given an explicit, bounded height (needed for its weighted rows); it is the
 *   available height, clamped between [TrackerLayout.CalendarMinHeight] and width x aspect cap.
 * - Display-cutout insets keep both panes clear of a notch in landscape.
 */
@Composable
fun TrackerWideContent(
    state: PrayerTrackerContract.State,
    onIntent: (Intent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(TrackerLayout.PaneSpacing),
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    horizontal = screenHorizontalMargin(),
                    vertical = 4.dp
                )
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            val calendarWidth: Dp =
                maxWidth.coerceAtMost(TrackerLayout.PaneContentMaxWidth)
            val calendarHeight: Dp =
                maxHeight
                    .coerceAtMost(
                        calendarWidth * TrackerLayout.CalendarMaxHeightPerWidth
                    )
                    .coerceAtLeast(
                        TrackerLayout.CalendarMinHeight
                    )

            Box(
                contentAlignment = Alignment.CenterStart,
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

        BoxWithConstraints(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            val listWidth: Dp =
                maxWidth.coerceAtMost(TrackerLayout.PaneContentMaxWidth)

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(listWidth)
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
}