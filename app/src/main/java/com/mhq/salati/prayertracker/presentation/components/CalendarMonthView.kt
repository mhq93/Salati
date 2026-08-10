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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

val SelectedCellBackground = Color(0xFF4A4A4A)
val CardBackgroundLocal = Color(0xFFFFFFFF)
val CardBorder = Color(0xFFE7E7E7)
val WeekendBackground = Color(0xFFF3F3F3)
val AdjacentMonthText = Color(0xFFBFBFBF)

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
        // Header: Gregorian month/year primary, Hijri month/year secondary
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onMonthChanged(-1) }) {
                Icon(
                    Icons.Filled.ChevronLeft,
                    contentDescription = "Previous month",
                    tint = InkText
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${
                        selectedMonth.month.getDisplayName(TextStyle.FULL, locale)
                    } ${selectedMonth.year}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = InkText
                )
                Text(
                    text = hijriHeaderFormatter.format(hijriOf(firstOfMonth)),
                    fontSize = 12.sp,
                    color = MutedSlate
                )
            }
            IconButton(onClick = { onMonthChanged(1) }) {
                Icon(Icons.Filled.ChevronRight, contentDescription = "Next month", tint = InkText)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Weekday headers, full names, Sun -> Sat
        // Note: since the grid now always starts Day 1 at col 0, these labels
        // no longer align to the actual weekday of each cell below them.
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf(
                DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
            ).forEach { dow ->
                Text(
                    text = dow.getDisplayName(TextStyle.SHORT, locale)
                        .replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
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
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(0.8f)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
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
                        // trailing slots past the last day of the month render nothing
                    }
                }
            }
        }
    }
}