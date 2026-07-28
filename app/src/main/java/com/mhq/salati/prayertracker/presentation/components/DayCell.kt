package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.shared.presentation.theme.AccentEmerald
import com.mhq.salati.shared.presentation.theme.CardBackground
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.MutedSlate
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun DayCell(
    gregorianDay: Int,
    hijriDay: Int,
    isSelected: Boolean,
    isCurrentMonth: Boolean,
    isWeekend: Boolean,
    status: DayStatus?,
    onClick: () -> Unit
) {

    val dotColor = when (status) {
        DayStatus.ALL_PRAYED -> AccentEmerald
        DayStatus.HAS_MISSED -> Color(0xFFD84C3E)
        DayStatus.IN_PROGRESS -> Color(0xFFE0A62E)
        else -> null
    }

    val cellBackground = when {
        isSelected -> SelectedCellBackground
        isWeekend -> WeekendBackground
        else -> CardBackgroundLocal
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(cellBackground)
            .border(
                1.dp,
                CardBorder,
                RoundedCornerShape(10.dp)
            )
            .clickable(
                enabled = isCurrentMonth,
                onClick = onClick
            )
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = gregorianDay.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = when {
                isSelected -> Color.White
                !isCurrentMonth -> AdjacentMonthText
                else -> InkText
            }
        )
        Text(
            text = hijriDay.toString(),
            fontSize = 11.sp,
            color = when {
                isSelected -> Color.White.copy(alpha = 0.7f)
                !isCurrentMonth -> AdjacentMonthText
                else -> MutedSlate
            }
        )
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .size(4.dp)
                .then(
                    if (dotColor != null) Modifier.background(dotColor, CircleShape) else Modifier
                )
        )
    }
}

@Preview
@Composable
private fun DayCellPreview() {
    SalatiTheme() {
        DayCell(
            gregorianDay = 1,
            hijriDay = 1,
            isSelected = true,
            isCurrentMonth = true,
            isWeekend = true,
            status = DayStatus.ALL_PRAYED,
            onClick = {}
        )
    }
}