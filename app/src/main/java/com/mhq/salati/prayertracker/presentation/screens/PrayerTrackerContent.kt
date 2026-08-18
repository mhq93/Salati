package com.mhq.salati.prayertracker.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.components.CalendarMonthView
import com.mhq.salati.prayertracker.presentation.components.PrayerStatusList
import com.mhq.salati.prayertracker.presentation.components.StreakBanner
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.State
import com.mhq.salati.shared.presentation.components.BottomNavDefaults
import com.mhq.salati.shared.presentation.components.TabHeader
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.SheetBackground

@Composable
fun PrayerTrackerContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SheetBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = BottomNavDefaults.Height)
        ) {
            TabHeader(
                icon = Icons.Default.CheckCircle,
                title = stringResource(R.string.prayer_tracker),
                subtitle = stringResource(R.string.track_your_five_daily_prayers),
                trailingContent = {
                    if (state.currentStreak > 0) {
                        StreakBanner(state.currentStreak)
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SheetBackground)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 24.dp
                    )
            ) {
                CalendarMonthView(
                    selectedMonth = state.selectedMonth,
                    selectedDate = state.selectedDate,
                    dayStatus = state.monthDayStatus,
                    onDateSelected = { onIntent(Intent.DateSelected(it)) },
                    onMonthChanged = { onIntent(Intent.MonthChanged(it)) }
                )
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
                PrayerStatusList(
                    selectedDate = state.selectedDate,
                    records = state.selectedDateRecords,
                    onPrayerTapped = { onIntent(Intent.PrayerTileTapped(it)) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrayerTrackerContentPreview() {
    SalatiTheme() {
        PrayerTrackerContent(
            state = State(),
            onIntent = {}
        )
    }
}