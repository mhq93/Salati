package com.mhq.salati.prayertracker.presentation.components

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
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun DayCell(
    gregorianDay: Int,
    hijriDay: Int,
    isCurrentMonth: Boolean,
    isWeekend: Boolean,
    isSelected: Boolean,
    status: DayStatus?,
    onClick: () -> Unit
) {
    val dotColor = when (status) {
        DayStatus.ALL_PRAYED -> MaterialTheme.colorScheme.primary // <-- Replaced AccentEmerald
        DayStatus.HAS_MISSED -> MaterialTheme.colorScheme.error // <-- Replaced TomatoRed
        DayStatus.IN_PROGRESS -> MaterialTheme.colorScheme.tertiary // <-- Replaced SultanGold
        else -> null
    }

    val cellBackground = when {
        isSelected -> MaterialTheme.colorScheme.surfaceVariant // <-- Replaced Charcoal
        isWeekend -> MaterialTheme.colorScheme.surfaceVariant // <-- Replaced UltraLightGray
        else -> MaterialTheme.colorScheme.surface // <-- Replaced White
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(cellBackground)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant, // <-- Replaced LightGrayCardBorder
                RoundedCornerShape(8.dp)
            )
            .clickable(enabled = isCurrentMonth, onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = gregorianDay.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = when {
                isSelected -> MaterialTheme.colorScheme.onSurface // <-- Replaced Color.White
                !isCurrentMonth -> MaterialTheme.colorScheme.outline // <-- Replaced Timberwolf
                else -> MaterialTheme.colorScheme.onSurface // <-- Replaced InkText
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = hijriDay.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = when {
                isSelected -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) // <-- Replaced Color.White.copy
                !isCurrentMonth -> MaterialTheme.colorScheme.outline // <-- Replaced Timberwolf
                else -> MaterialTheme.colorScheme.onSurfaceVariant // <-- Replaced MutedSlate
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .size(4.dp)
                .then(if (dotColor != null) Modifier.background(dotColor, CircleShape) else Modifier)
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
            isWeekend = true,
            status = DayStatus.ALL_PRAYED,
            onClick = {}
        )
    }
}

//package com.mhq.salati.prayertracker.presentation.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.prayertracker.domain.model.DayStatus
//import com.mhq.salati.shared.presentation.theme.AccentEmerald
//import com.mhq.salati.shared.presentation.theme.White
//import com.mhq.salati.shared.presentation.theme.LightGrayCardBorder
//import com.mhq.salati.shared.presentation.theme.InkText
//import com.mhq.salati.shared.presentation.theme.MutedSlate
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//import com.mhq.salati.shared.presentation.theme.Charcoal
//import com.mhq.salati.shared.presentation.theme.SultanGold
//import com.mhq.salati.shared.presentation.theme.Timberwolf
//import com.mhq.salati.shared.presentation.theme.TomatoRed
//import com.mhq.salati.shared.presentation.theme.UltraLightGray
//
//@Composable
//fun DayCell(
//    gregorianDay: Int,
//    hijriDay: Int,
//    isCurrentMonth: Boolean,
//    isWeekend: Boolean,
//    isSelected: Boolean,
//    status: DayStatus?,
//    onClick: () -> Unit
//) {
//
//    val dotColor = when (status) {
//        DayStatus.ALL_PRAYED -> AccentEmerald
//        DayStatus.HAS_MISSED -> TomatoRed
//        DayStatus.IN_PROGRESS -> SultanGold
//        else -> null
//    }
//
//    val cellBackground = when {
//        isSelected -> Charcoal
//        isWeekend -> UltraLightGray
//        else -> White
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .fillMaxSize()
//            .clip(RoundedCornerShape(8.dp))//10
//            .background(cellBackground)
//            .border(
//                1.dp,
//                LightGrayCardBorder,
//                RoundedCornerShape(8.dp)//10
//            )
//            .clickable(
//                enabled = isCurrentMonth,
//                onClick = onClick
//            )
//            .padding(vertical = 4.dp)//6
//    ) {
//        Text(
//            text = gregorianDay.toString(),
//            fontSize = 20.sp,//15
//            fontWeight = FontWeight.Medium,
//            color = when {
//                isSelected -> Color.White
//                !isCurrentMonth -> Timberwolf
//                else -> InkText
//            }
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = hijriDay.toString(),
//            fontSize = 16.sp,//11
//            fontWeight = FontWeight.Light,
//            color = when {
//                isSelected -> Color.White.copy(alpha = 0.7f)
//                !isCurrentMonth -> Timberwolf
//                else -> MutedSlate
//            }
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Box(
//            modifier = Modifier
//                .size(4.dp)
//                .then(
//                    if (dotColor != null) Modifier.background(dotColor, CircleShape) else Modifier
//                )
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun DayCellPreview() {
//    SalatiTheme() {
//        DayCell(
//            gregorianDay = 1,
//            hijriDay = 1,
//            isSelected = true,
//            isCurrentMonth = true,
//            isWeekend = true,
//            status = DayStatus.ALL_PRAYED,
//            onClick = {}
//        )
//    }
//}