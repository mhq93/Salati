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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.shared.ui.components.ShrinkToFitText
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.HijrahChronology
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.time.temporal.WeekFields

/**
 * Month grid with Gregorian + Hijri dates.
 *
 * - Tall screens: square cells, width comes from the caller ([modifier]).
 * - Wide screens: the CALLER must give this a bounded height (see PrayerTrackerContent); the rows
 *   then share that height equally with weight(1f).
 *
 * Header and weekday labels are single-line and shrink to fit, so Arabic names (long short-day
 * names, long Hijri month names) never wrap and grow the header.
 */
@Composable
fun CalendarMonthView(
    selectedMonth: YearMonth,
    selectedDate: LocalDate,
    today: LocalDate,
    dayStatus: Map<LocalDate, DayStatusUi>,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    hijriOffsetDays: Int = 0,
) {
    val isWide = isWideLayout()

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

    val gregorianHeader = "${selectedMonth.month.getDisplayName(TextStyle.FULL, locale)} ${selectedMonth.year}"
    val hijriHeader = hijriHeaderFormatter.format(hijriDatesMap[firstDayOfTheMonth]!!)

    Column(modifier = modifier) {
        Row(
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

            if (isWide) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.weight(1f)
                ) {
                    ShrinkToFitText(
                        text = gregorianHeader,
                        fontSize = 16.sp,
                        minFontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    ShrinkToFitText(
                        text = hijriHeader,
                        fontSize = 13.sp,
                        minFontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1.4f, fill = false)
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    ShrinkToFitText(
                        text = gregorianHeader,
                        fontSize = 20.sp,
                        minFontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    ShrinkToFitText(
                        text = hijriHeader,
                        fontSize = 16.sp,
                        minFontSize = 12.sp,
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
        Spacer(modifier = Modifier.height(if (isWide) 1.dp else 8.dp))

        val weekDays = (0 until 7).map { firstDayOfTheWeek.plus(it.toLong()) }
        Row(modifier = Modifier.fillMaxWidth()) {
            weekDays.forEach { dayOfWeek ->
                ShrinkToFitText(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                        .replaceFirstChar { it.uppercase() },
                    fontSize = if (isWide) 12.sp else 16.sp,
                    minFontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(if (isWide) 2.dp else 6.dp))

        for (row in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (isWide) Modifier.weight(1f) else Modifier)
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
                            .then(if (isWide) Modifier.fillMaxHeight() else Modifier.aspectRatio(1f))
                            .padding(if (isWide) 1.dp else 2.dp)
                    ) {
                        CalendarDayCell(
                            gregorianDay = cellDate.dayOfMonth,
                            hijriDay = hijriDate.get(ChronoField.DAY_OF_MONTH),
                            isSelected = isCurrentMonth && cellDate == selectedDate,
                            isFuture = cellDate.isAfter(today),
                            isCurrentMonth = isCurrentMonth,
                            status = if (isCurrentMonth) dayStatus[cellDate] ?: DayStatusUi.FUTURE else null,
                            onClick = { onDateSelected(cellDate) },
                            modifier = if (isWide) Modifier.padding(vertical = 1.dp) else Modifier
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