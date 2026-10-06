package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.domain.model.DayStatus
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.HijrahChronology
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.time.temporal.WeekFields

@Composable
fun CalendarMonthView(
    selectedMonth: YearMonth,
    selectedDate: LocalDate,
    dayStatus: Map<LocalDate, DayStatus>,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (Int) -> Unit,
    hijriOffsetDays: Int = 0
) {
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

    Column {
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

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${
                        selectedMonth.month.getDisplayName(
                            TextStyle.FULL,
                            locale
                        )
                    } ${selectedMonth.year}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    // Uses the pre-calculated map to avoid heavy HijrahChronology math on recomposition
                    text = hijriHeaderFormatter.format(hijriDatesMap[firstDayOfTheMonth]!!),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = { onMonthChanged(1) }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.next_month),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        val weekDays = (0 until 7).map { firstDayOfTheWeek.plus(it.toLong()) }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            weekDays.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                        .replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))

        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val cellIndex = row * 7 + col
                    val cellDate = gridDates[cellIndex]
                    val hijriDate = hijriDatesMap[cellDate]!!
                    val isCurrentMonth = cellDate.month == selectedMonth.month

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(2.dp)
                    ) {
                        DayCell(
                            gregorianDay = cellDate.dayOfMonth,
                            hijriDay = hijriDate.get(ChronoField.DAY_OF_MONTH),
                            isSelected = isCurrentMonth && cellDate == selectedDate,
                            isCurrentMonth = isCurrentMonth,
                            status = if (isCurrentMonth) dayStatus[cellDate]
                                ?: DayStatus.FUTURE else null,
                            onClick = { onDateSelected(cellDate) }
                        )
                    }
                }
            }
        }
    }
}