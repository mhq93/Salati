package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun DayCell(
    gregorianDay: Int,
    hijriDay: Int,
    isCurrentMonth: Boolean,
    isSelected: Boolean,
    status: DayStatus?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dotIndicatorColor = when (status) {
        DayStatus.ALL_PRAYED -> MaterialTheme.colorScheme.primary
        DayStatus.HAS_MISSED -> MaterialTheme.colorScheme.error
        DayStatus.IN_PROGRESS -> MaterialTheme.colorScheme.tertiary
        else -> null
    }

    val cellBackgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        !isCurrentMonth -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }

    val baseTextColor = MaterialTheme.colorScheme.onSurface

    val cellBorderColor = if (isCurrentMonth) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.15f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(cellBackgroundColor)
            .border(
                1.dp,
                cellBorderColor,
                RoundedCornerShape(8.dp)
            )
            .clickable(enabled = isCurrentMonth, onClick = onClick)
            .padding(4.dp)
    ) {
        Text(
            text = gregorianDay.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            // [FIX 3]: Native alpha blending for out-of-month days
            color = when {
                isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                !isCurrentMonth -> baseTextColor.copy(alpha = 0.38f)
                else -> baseTextColor
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = hijriDay.toString(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Light,
            color = when {
                isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                !isCurrentMonth -> baseTextColor.copy(alpha = 0.24f)
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .size(4.dp)
                .then(
                    if (dotIndicatorColor != null)
                        Modifier.background(dotIndicatorColor, CircleShape)
                    else
                        Modifier
                )
        )
    }
}

@Preview
@Composable
private fun DayCellPreview() {
    SalatiTheme {
        DayCell(
            gregorianDay = 1,
            hijriDay = 1,
            isSelected = true,
            isCurrentMonth = true,
            status = DayStatus.ALL_PRAYED,
            onClick = {}
        )
    }
}