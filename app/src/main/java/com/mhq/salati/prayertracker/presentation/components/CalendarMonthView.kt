package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.MutedSlate
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle

@Composable
fun CalendarMonthView(
    selectedMonth: YearMonth,
    selectedDate: LocalDate,
    dayStatus: Map<LocalDate, DayStatus>,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (Int) -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onMonthChanged(-1) }) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = "Previous month",
                    tint = InkText
                )
            }
            Text(
                text = "${
                    selectedMonth.month.getDisplayName(
                        TextStyle.FULL,
                        LocalLocale.current.platformLocale
                    )
                } ${selectedMonth.year}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = InkText
            )
            IconButton(onClick = { onMonthChanged(1) }) {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Next month",
                    tint = InkText
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = MutedSlate,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        val firstOfMonth = selectedMonth.atDay(1)
        val leadingBlanks = firstOfMonth.dayOfWeek.value % 7
        val totalDays = selectedMonth.lengthOfMonth()
        val cells = leadingBlanks + totalDays
        val rows = (cells + 6) / 7

        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {

                for (col in 0 until 7) {
                    val cellIndex = row * 7 + col
                    val dayNum = cellIndex - leadingBlanks + 1

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f), contentAlignment = Alignment.Center
                    ) {
                        if (dayNum in 1..totalDays) {
                            val date = selectedMonth.atDay(dayNum)

                            DayCell(
                                day = dayNum,
                                isSelected = date == selectedDate,
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

@Preview
@Composable
private fun CalendarMonthViewPreview() {
    SalatiTheme() {
        CalendarMonthView(
            selectedMonth = YearMonth.now(),
            selectedDate = LocalDate.now(),
            dayStatus = emptyMap(),
            onDateSelected = {},
            onMonthChanged = {}
        )
    }
}