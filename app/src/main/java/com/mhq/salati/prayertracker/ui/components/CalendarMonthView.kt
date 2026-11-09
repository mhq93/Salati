package com.mhq.salati.prayertracker.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.HijrahChronology
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.time.temporal.WeekFields

//Different Sizes and Device Rotation
@Composable
fun CalendarMonthView(
    selectedMonth: YearMonth,
    selectedDate: LocalDate,
    today: LocalDate,
    dayStatus: Map<LocalDate, DayStatusUi>,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (Int) -> Unit,
    hijriOffsetDays: Int = 0,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val locale = LocalLocale.current.platformLocale
    val hijriChronology = HijrahChronology.INSTANCE

    val firstDayOfTheWeek = WeekFields.of(locale).firstDayOfWeek
    val firstDayOfTheWeekValue = firstDayOfTheWeek.value
    val firstDayOfTheMonth = selectedMonth.atDay(1)
    val firstOfTheMonthDayValue = firstDayOfTheMonth.dayOfWeek.value
    val firstDayOfWeekIndex = (firstOfTheMonthDayValue - firstDayOfTheWeekValue + 7) % 7
    val totalDays = selectedMonth.lengthOfMonth()

    val rows = (totalDays + firstDayOfWeekIndex + 6) / 7

    val gridDates = remember(selectedMonth, firstDayOfWeekIndex, rows) {
        val dates = mutableListOf<LocalDate>()
        for (row in 0 until rows) {
            for (col in 0 until 7) {
                val cellIndex = row * 7 + col
                val dayNum = cellIndex - firstDayOfWeekIndex + 1
                val cellDate = when {
                    dayNum < 1 -> {
                        val previousMonth = selectedMonth.minusMonths(1)
                        previousMonth.atDay(previousMonth.lengthOfMonth() + dayNum)
                    }
                    dayNum > totalDays -> {
                        selectedMonth.plusMonths(1).atDay(dayNum - totalDays)
                    }
                    else -> selectedMonth.atDay(dayNum)
                }
                dates.add(cellDate)
            }
        }
        dates
    }

    val hijriDatesMap = remember(gridDates, hijriOffsetDays) {
        gridDates.associateWith { date ->
            hijriChronology.date(date.plusDays(hijriOffsetDays.toLong()))
        }
    }

    val hijriHeaderFormatter = remember(locale) { DateTimeFormatter.ofPattern("MMMM y", locale) }

    // Landscape: fill the full height so the grid rows can share the space evenly.
    // This keeps every day cell the same size and stops the last row from
    // being pushed against the bottom nav bar.
    Column(
        modifier = Modifier.then(if (isLandscape) Modifier.fillMaxHeight() else Modifier)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onMonthChanged(-1) }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.previous_month),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            if (isLandscape) {
                // Single-line header: Gregorian month first, then Hijri month on the same line
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${selectedMonth.month.getDisplayName(TextStyle.FULL, locale)} ${selectedMonth.year}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = hijriHeaderFormatter.format(hijriDatesMap[firstDayOfTheMonth]!!),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${selectedMonth.month.getDisplayName(TextStyle.FULL, locale)} ${selectedMonth.year}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = hijriHeaderFormatter.format(hijriDatesMap[firstDayOfTheMonth]!!),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            val canGoForward = selectedMonth.isBefore(YearMonth.from(today))
            IconButton(onClick = { onMonthChanged(1) }, enabled = canGoForward) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.next_month),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = if (canGoForward) 1f else 0.38f)
                )
            }
        }
        Spacer(modifier = Modifier.height(if (isLandscape) 1.dp else 8.dp))

        val weekDays = (0 until 7).map { firstDayOfTheWeek.plus(it.toLong()) }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            weekDays.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                        .replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center,
                    fontSize = if (isLandscape) 12.sp else 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(if (isLandscape) 2.dp else 6.dp))

        for (row in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // Landscape: equal share of the remaining height for every row
                    .then(if (isLandscape) Modifier.weight(1f) else Modifier)
            ) {
                for (col in 0 until 7) {
                    val cellIndex = row * 7 + col
                    val cellDate = gridDates[cellIndex]
                    val hijriDate = hijriDatesMap[cellDate]!!
                    val isCurrentMonth = cellDate.month == selectedMonth.month

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .then(if (isLandscape) Modifier.fillMaxHeight() else Modifier.aspectRatio(1f))
                            .padding(if (isLandscape) 1.dp else 2.dp)
                    ) {
                        CalendarDayCell(
                            gregorianDay = cellDate.dayOfMonth,
                            hijriDay = hijriDate.get(ChronoField.DAY_OF_MONTH),
                            isSelected = isCurrentMonth && cellDate == selectedDate,
                            isFuture = cellDate.isAfter(today),
                            isCurrentMonth = isCurrentMonth,
                            status = if (isCurrentMonth) dayStatus[cellDate] ?: DayStatusUi.FUTURE else null,
                            onClick = { onDateSelected(cellDate) },
                            modifier = if (isLandscape) Modifier.padding(vertical = 1.dp) else Modifier
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CalendarMonthViewPreview() {
    SalatiTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        ) {
            CalendarMonthView(
                selectedMonth = YearMonth.of(2026, 10),
                selectedDate = LocalDate.of(2026, 10, 8),
                today = LocalDate.of(2026, 10, 8),
                dayStatus = mapOf(
                    LocalDate.of(2026, 10, 7) to DayStatusUi.ALL_PRAYED,
                    LocalDate.of(2026, 10, 8) to DayStatusUi.IN_PROGRESS
                ),
                onDateSelected = {},
                onMonthChanged = {},
            )
        }
    }
}



//package com.mhq.salati.prayertracker.ui.components
//
//import android.content.res.Configuration
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalLocale
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.R
//import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
//import com.mhq.salati.shared.ui.theme.SalatiTheme
//import java.time.LocalDate
//import java.time.YearMonth
//import java.time.chrono.HijrahChronology
//import java.time.format.DateTimeFormatter
//import java.time.format.TextStyle
//import java.time.temporal.ChronoField
//import java.time.temporal.WeekFields
//
////Original
//@Composable
//fun CalendarMonthView(
//    selectedMonth: YearMonth,
//    selectedDate: LocalDate,
//    today: LocalDate,
//    dayStatus: Map<LocalDate, DayStatusUi>,
//    onDateSelected: (LocalDate) -> Unit,
//    onMonthChanged: (Int) -> Unit,
//    hijriOffsetDays: Int = 0,
//) {
//    val locale = LocalLocale.current.platformLocale
//    val hijriChronology = HijrahChronology.INSTANCE
//
//    val firstDayOfTheWeek = WeekFields.of(locale).firstDayOfWeek
//    val firstDayOfTheWeekValue = firstDayOfTheWeek.value
//    val firstDayOfTheMonth = selectedMonth.atDay(1)
//    val firstOfTheMonthDayValue = firstDayOfTheMonth.dayOfWeek.value
//    val firstDayOfWeekIndex = (firstOfTheMonthDayValue - firstDayOfTheWeekValue + 7) % 7
//    val totalDays = selectedMonth.lengthOfMonth()
//
//    val rows = (totalDays + firstDayOfWeekIndex + 6) / 7
//
//    val gridDates = remember(selectedMonth, firstDayOfWeekIndex, rows) {
//        val dates = mutableListOf<LocalDate>()
//        for (row in 0 until rows) {
//            for (col in 0 until 7) {
//                val cellIndex = row * 7 + col
//                val dayNum = cellIndex - firstDayOfWeekIndex + 1
//                val cellDate = when {
//                    dayNum < 1 -> {
//                        val previousMonth = selectedMonth.minusMonths(1)
//                        previousMonth.atDay(previousMonth.lengthOfMonth() + dayNum)
//                    }
//
//                    dayNum > totalDays -> {
//                        selectedMonth.plusMonths(1).atDay(dayNum - totalDays)
//                    }
//
//                    else -> selectedMonth.atDay(dayNum)
//                }
//                dates.add(cellDate)
//            }
//        }
//        dates
//    }
//
//    val hijriDatesMap = remember(gridDates, hijriOffsetDays) {
//        gridDates.associateWith { date ->
//            hijriChronology.date(date.plusDays(hijriOffsetDays.toLong()))
//        }
//    }
//
//    val hijriHeaderFormatter = remember(locale) { DateTimeFormatter.ofPattern("MMMM y", locale) }
//
//    Column {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            IconButton(onClick = { onMonthChanged(-1) }) {
//                Icon(
//                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                    contentDescription = stringResource(R.string.previous_month),
//                    tint = MaterialTheme.colorScheme.onSurface
//                )
//            }
//
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(
//                    text = "${
//                        selectedMonth.month.getDisplayName(
//                            TextStyle.FULL,
//                            locale
//                        )
//                    } ${selectedMonth.year}",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//                Text(
//                    // Uses the pre-calculated map to avoid heavy HijrahChronology math on recomposition
//                    text = hijriHeaderFormatter.format(hijriDatesMap[firstDayOfTheMonth]!!),
//                    fontSize = 16.sp,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//            val canGoForward = selectedMonth.isBefore(YearMonth.from(today))
//            IconButton(onClick = { onMonthChanged(1) }, enabled = canGoForward) {
//                Icon(
//                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                    contentDescription = stringResource(R.string.next_month),
//                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = if (canGoForward) 1f else 0.38f)
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//
//        val weekDays = (0 until 7).map { firstDayOfTheWeek.plus(it.toLong()) }
//        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
//            weekDays.forEach { dayOfWeek ->
//                Text(
//                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
//                        .replaceFirstChar { it.uppercase() },
//                    textAlign = TextAlign.Center,
//                    fontSize = 16.sp,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(6.dp))
//
//        for (row in 0 until rows) {
//            Row(modifier = Modifier.fillMaxWidth()) {
//                for (col in 0 until 7) {
//                    val cellIndex = row * 7 + col
//                    val cellDate = gridDates[cellIndex]
//                    val hijriDate = hijriDatesMap[cellDate]!!
//                    val isCurrentMonth = cellDate.month == selectedMonth.month
//
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .weight(1f)
//                            .aspectRatio(1f)
//                            .padding(2.dp)
//                    ) {
//                        DayCell(
//                            gregorianDay = cellDate.dayOfMonth,
//                            hijriDay = hijriDate.get(ChronoField.DAY_OF_MONTH),
//                            isSelected = isCurrentMonth && cellDate == selectedDate,
//                            isFuture = cellDate.isAfter(today),
//                            isCurrentMonth = isCurrentMonth,
//                            status = if (isCurrentMonth) dayStatus[cellDate]
//                                ?: DayStatusUi.FUTURE else null,
//                            onClick = { onDateSelected(cellDate) }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview(name = "Light Mode", showBackground = true)
//@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun CalendarMonthViewPreview() {
//    SalatiTheme {
//        Box(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.background)
//                .padding(12.dp)
//        ) {
//            CalendarMonthView(
//                selectedMonth = YearMonth.of(2026, 10),
//                selectedDate = LocalDate.of(2026, 10, 8),
//                today = LocalDate.of(2026, 10, 8),
//                dayStatus = mapOf(
//                    LocalDate.of(2026, 10, 7) to DayStatusUi.ALL_PRAYED,
//                    LocalDate.of(2026, 10, 8) to DayStatusUi.IN_PROGRESS
//                ),
//                onDateSelected = {},
//                onMonthChanged = {},
//            )
//        }
//    }
//}