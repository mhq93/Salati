package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.shared.presentation.theme.AccentEmerald
import com.mhq.salati.shared.presentation.theme.CardBackground
import com.mhq.salati.shared.presentation.theme.InkText

@Composable
fun DayCell(day: Int, isSelected: Boolean, status: DayStatus, onClick: () -> Unit) {
    val dotColor = when (status) {
        DayStatus.ALL_PRAYED -> AccentEmerald
        DayStatus.HAS_MISSED -> androidx.compose.ui.graphics.Color(0xFFD84C3E)
        DayStatus.IN_PROGRESS -> androidx.compose.ui.graphics.Color(0xFFE0A62E)
        DayStatus.FUTURE -> null
    }
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(if (isSelected) CardBackground else androidx.compose.ui.graphics.Color.Transparent)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = day.toString(), fontSize = 13.sp, color = InkText)
            if (dotColor != null) {
                Box(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
    }
}