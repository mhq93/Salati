package com.mhq.salati.prayertracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.State
import com.mhq.salati.prayertracker.ui.components.CalendarMonthView
import com.mhq.salati.prayertracker.ui.components.PrayerStatusList
import com.mhq.salati.prayertracker.ui.components.StreakBanner
import com.mhq.salati.prayertracker.ui.screens.TrackerLayout.TrackerPortraitMaxWidth
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth

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
                        .padding(horizontal = ScreenHorizontalPadding, vertical = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    CalendarMonthView(
                        today = state.today,
                        dayStatus = state.monthDayStatus,
                        selectedDate = state.selectedDate,
                        selectedMonth = state.selectedMonth,
                        onDateSelected = { onIntent(Intent.DateSelected(it)) },
                        onMonthChanged = { onIntent(Intent.MonthChanged(it)) },
                        hijriOffsetDays = state.hijriDateOffset,
                        modifier = Modifier
                            .widthIn(max = TrackerPortraitMaxWidth)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    PrayerStatusList(
                        today = state.today,
                        selectedDate = state.selectedDate,
                        records = state.selectedDateRecords,
                        duePrayers = state.duePrayers,
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

@Preview(
    name = "Portrait",
    showSystemUi = true
)
@Preview(
    name = "Landscape",
    device = "spec:width=800dp,height=400dp,dpi=440",
    showSystemUi = true
)
@Preview(
    name = "Small landscape",
    device = "spec:width=640dp,height=320dp,dpi=420",
    showSystemUi = true
)
@Preview(
    name = "Tablet",
    device = "spec:width=1280dp,height=800dp,dpi=240",
    showSystemUi = true
)
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