package com.mhq.salati.prayertracker.presentation.components

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
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.MutedSlate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.HijrahChronology
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.Locale

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
    fun hijriOf(date: LocalDate): HijrahDate =
        hijriChronology.date(date.plusDays(hijriOffsetDays.toLong()))

    val firstOfMonth = selectedMonth.atDay(1)
    val lastOfMonth = selectedMonth.atEndOfMonth()
    val totalDays = selectedMonth.lengthOfMonth()

    // Grid always starts at Day 1 in the top-left cell — no leading blanks.
    val rows = (totalDays + 6) / 7
    val trailing = rows * 7 - totalDays
    val gridEnd = lastOfMonth.plusDays(trailing.toLong())

    val hijriHeaderFormatter = remember(locale) { DateTimeFormatter.ofPattern("MMMM y", locale) }
    val gregorianRangeFormatter = remember(locale) { DateTimeFormatter.ofPattern("MMM d", locale) }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onMonthChanged(-1) }) {
                Icon(
                    Icons.Filled.ChevronLeft,
                    contentDescription = stringResource(R.string.previous_month),
                    tint = InkText
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${
                        selectedMonth.month.getDisplayName(
                            TextStyle.FULL,
                            locale
                        )
                    } ${selectedMonth.year}",
                    fontSize = 20.sp,//16
                    fontWeight = FontWeight.Medium,
                    color = InkText
                )
                Text(
                    text = hijriHeaderFormatter.format(hijriOf(firstOfMonth)),
                    fontSize = 16.sp,//12
                    color = MutedSlate
                )
            }
            IconButton(onClick = { onMonthChanged(1) }) {
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = stringResource(R.string.next_month),
                    tint = InkText
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
            ).forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                        .replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,//12
                    color = MutedSlate,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val dayNum = row * 7 + col + 1
                    val isCurrentMonth = dayNum in 1..totalDays

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(0.8f)
                            .padding(2.dp)
                    ) {
                        if (isCurrentMonth) {
                            val date = selectedMonth.atDay(dayNum)
                            val isWeekend =
                                date.dayOfWeek == DayOfWeek.FRIDAY || date.dayOfWeek == DayOfWeek.SATURDAY

                            DayCell(
                                gregorianDay = date.dayOfMonth,
                                hijriDay = hijriOf(date).get(ChronoField.DAY_OF_MONTH),
                                isSelected = date == selectedDate,
                                isCurrentMonth = true,
                                isWeekend = isWeekend,
                                status = dayStatus[date] ?: DayStatus.FUTURE,
                                onClick = { onDateSelected(date) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarMonthViewPreview() {
    val currentMonth = YearMonth.now()
    val today = LocalDate.now()

    val mockDayStatus = mapOf(
        today.minusDays(2) to DayStatus.ALL_PRAYED,
        today.minusDays(1) to DayStatus.ALL_PRAYED,
        today to DayStatus.FUTURE,
        today.plusDays(1) to DayStatus.FUTURE
    )

    CalendarMonthView(
        selectedMonth = currentMonth,
        selectedDate = today,
        dayStatus = mockDayStatus,
        onDateSelected = {},
        onMonthChanged = {},
        hijriOffsetDays = 0
    )
}