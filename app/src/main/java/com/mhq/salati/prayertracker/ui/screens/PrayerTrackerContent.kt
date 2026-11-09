package com.mhq.salati.prayertracker.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.State
import com.mhq.salati.prayertracker.ui.components.CalendarMonthView
import com.mhq.salati.prayertracker.ui.components.PrayerStatusList
import com.mhq.salati.prayertracker.ui.components.StreakBanner
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth

//Different Sizes and Device Rotation
@Composable
fun PrayerTrackerContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

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

            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Column: Caps total width to force square aspect ratio to shrink cells uniformly
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .widthIn(max = 300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CalendarMonthView(
                            selectedMonth = state.selectedMonth,
                            selectedDate = state.selectedDate,
                            today = state.today,
                            dayStatus = state.monthDayStatus,
                            onDateSelected = { onIntent(Intent.DateSelected(it)) },
                            onMonthChanged = { onIntent(Intent.MonthChanged(it)) },
                            hijriOffsetDays = state.hijriDateOffset
                        )
                    }

                    // Separating space between the calendar and the prayer checklist
                    Spacer(modifier = Modifier.width(24.dp))

                    // Right Column: Symmetrical vertical checklist
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
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
            } else {
                Column(
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
                        hijriOffsetDays = state.hijriDateOffset
                    )
                    Spacer(modifier = Modifier.height(24.dp))
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
}

@Preview(name = "Portrait", showSystemUi = true)
@Preview(name = "Landscape", device = "spec:width=800dp,height=400dp,dpi=440", showSystemUi = true)
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





//package com.mhq.salati.prayertracker.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.mhq.salati.R
//import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
//import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.State
//import com.mhq.salati.prayertracker.ui.components.CalendarMonthView
//import com.mhq.salati.prayertracker.ui.components.PrayerStatusList
//import com.mhq.salati.prayertracker.ui.components.StreakBanner
//import com.mhq.salati.shared.ui.components.TabHeader
//import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
//import com.mhq.salati.shared.ui.theme.SalatiTheme
//import java.time.LocalDate
//import java.time.YearMonth
//
////Original
//@Composable
//fun PrayerTrackerContent(
//    state: State,
//    onIntent: (Intent) -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = MaterialTheme.colorScheme.background)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .navigationBarsPadding()
//                .padding(bottom = BottomNavBarDefaults.Height)
//        ) {
//            TabHeader(
//                icon = Icons.Default.CheckCircle,
//                title = stringResource(R.string.tracker),
//                subtitle = stringResource(R.string.track_your_five_daily_prayers),
//                trailingContent = {
//                    if (state.currentStreak > 0) {
//                        StreakBanner(state.currentStreak)
//                    }
//                }
//            )
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(MaterialTheme.colorScheme.background)
//                    .padding(horizontal = 20.dp, vertical = 24.dp)
//            ) {
//                CalendarMonthView(
//                    selectedMonth = state.selectedMonth,
//                    selectedDate = state.selectedDate,
//                    today = state.today,
//                    dayStatus = state.monthDayStatus,
//                    onDateSelected = { onIntent(Intent.DateSelected(it)) },
//                    onMonthChanged = { onIntent(Intent.MonthChanged(it)) },
//                    hijriOffsetDays = state.hijriDateOffset
//                )
//                Spacer(modifier = Modifier.height(24.dp))
//                PrayerStatusList(
//                    duePrayers = state.duePrayers,
//                    selectedDate = state.selectedDate,
//                    today = state.today,
//                    records = state.selectedDateRecords,
//                    onPrayerTapped = { onIntent(Intent.PrayerSelected(it)) }
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun PrayerTrackerContentPreview() {
//    SalatiTheme() {
//        PrayerTrackerContent(
//            state = State(
//                today = LocalDate.of(2026, 10, 8),
//                selectedMonth = YearMonth.of(2026, 10),
//                selectedDate = LocalDate.of(2026, 10, 8)
//            ),
//            onIntent = {}
//        )
//    }
//}