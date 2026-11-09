package com.mhq.salati.prayertracker.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.shared.ui.theme.SalatiTheme

//Different Sizes and Device Rotation
@Composable
fun CalendarDayCell(
    gregorianDay: Int,
    hijriDay: Int,
    isCurrentMonth: Boolean,
    isFuture: Boolean = false,
    isSelected: Boolean,
    status: DayStatusUi?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val dotIndicatorColor = when (status) {
        DayStatusUi.ALL_PRAYED -> MaterialTheme.colorScheme.primary
        DayStatusUi.HAS_MISSED -> MaterialTheme.colorScheme.error
        DayStatusUi.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
        else -> null
    }

    val cellBackgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        !isCurrentMonth -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }

    val baseTextColor = MaterialTheme.colorScheme.onSurface

    val cellBorderColor = if (isCurrentMonth && !isFuture) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.15f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .then(if (isLandscape) Modifier.fillMaxSize() else Modifier.aspectRatio(1f))
            .clip(RoundedCornerShape(8.dp))
            .background(cellBackgroundColor)
            .border(
                1.dp,
                cellBorderColor,
                RoundedCornerShape(8.dp)
            )
            .clickable(enabled = isCurrentMonth && !isFuture, onClick = onClick)
            .padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        if (isLandscape) {
            // Landscape: Gregorian day then Hijri day, centered as a group
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = gregorianDay.toString(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = when {
                        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.38f)
                        else -> baseTextColor
                    }
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = hijriDay.toString(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Light,
                    color = when {
                        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.24f)
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            // Status dot pinned to the bottom center of the cell
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 3.dp)
                    .size(3.dp)
                    .then(
                        if (dotIndicatorColor != null)
                            Modifier.background(dotIndicatorColor, CircleShape)
                        else
                            Modifier
                    )
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Number stack centered in the space above the dot, so the
                // Hijri number always sits high with clear room below it
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = gregorianDay.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = when {
                            isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                            !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.38f)
                            else -> baseTextColor
                        }
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = hijriDay.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light,
                        color = when {
                            isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.24f)
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }

                // Status dot pinned to the bottom center of the cell
                Box(
                    modifier = Modifier
                        .padding(bottom = 2.dp)
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
    }
}

@Preview
@Composable
private fun CalendarDayCellPreview() {
    SalatiTheme {
        CalendarDayCell(
            gregorianDay = 1,
            hijriDay = 1,
            isSelected = true,
            isCurrentMonth = true,
            status = DayStatusUi.ALL_PRAYED,
            onClick = {}
        )
    }
}

//package com.mhq.salati.prayertracker.ui.components
//
//import android.content.res.Configuration
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
//import com.mhq.salati.shared.ui.theme.SalatiTheme
//
////Different Sizes and Device Rotation
//@Composable
//fun CalendarDayCell(
//    gregorianDay: Int,
//    hijriDay: Int,
//    isCurrentMonth: Boolean,
//    isFuture: Boolean = false,
//    isSelected: Boolean,
//    status: DayStatusUi?,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val configuration = LocalConfiguration.current
//    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
//
//    val dotIndicatorColor = when (status) {
//        DayStatusUi.ALL_PRAYED -> MaterialTheme.colorScheme.primary
//        DayStatusUi.HAS_MISSED -> MaterialTheme.colorScheme.error
//        DayStatusUi.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
//        else -> null
//    }
//
//    val cellBackgroundColor = when {
//        isSelected -> MaterialTheme.colorScheme.primaryContainer
//        !isCurrentMonth -> MaterialTheme.colorScheme.surfaceVariant
//        else -> MaterialTheme.colorScheme.surface
//    }
//
//    val baseTextColor = MaterialTheme.colorScheme.onSurface
//
//    val cellBorderColor = if (isCurrentMonth && !isFuture) {
//        MaterialTheme.colorScheme.outlineVariant
//    } else {
//        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.15f)
//    }
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//            .then(if (isLandscape) Modifier.fillMaxSize() else Modifier.aspectRatio(1f))
//            .clip(RoundedCornerShape(8.dp))
//            .background(cellBackgroundColor)
//            .border(
//                1.dp,
//                cellBorderColor,
//                RoundedCornerShape(8.dp)
//            )
//            .clickable(enabled = isCurrentMonth && !isFuture, onClick = onClick)
//            .padding(vertical = if (isLandscape) 2.dp else 4.dp, horizontal = 2.dp)
//    ) {
//        if (isLandscape) {
//            // Landscape: Gregorian day then Hijri day, centered as a group
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = gregorianDay.toString(),
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = when {
//                        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
//                        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.38f)
//                        else -> baseTextColor
//                    }
//                )
//                Spacer(modifier = Modifier.width(6.dp))
//                Text(
//                    text = hijriDay.toString(),
//                    fontSize = 11.sp,
//                    fontWeight = FontWeight.Light,
//                    color = when {
//                        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
//                        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.24f)
//                        else -> MaterialTheme.colorScheme.onSurfaceVariant
//                    }
//                )
//            }
//
//            // Status dot pinned to the bottom center of the cell
//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 3.dp)
//                    .size(3.dp)
//                    .then(
//                        if (dotIndicatorColor != null)
//                            Modifier.background(dotIndicatorColor, CircleShape)
//                        else
//                            Modifier
//                    )
//            )
//        } else {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = gregorianDay.toString(),
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = when {
//                        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
//                        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.38f)
//                        else -> baseTextColor
//                    }
//                )
//                Spacer(modifier = Modifier.height(2.dp))
//                Text(
//                    text = hijriDay.toString(),
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.Light,
//                    color = when {
//                        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
//                        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.24f)
//                        else -> MaterialTheme.colorScheme.onSurfaceVariant
//                    }
//                )
//                Spacer(modifier = Modifier.height(2.dp))
//                Box(
//                    modifier = Modifier
//                        .size(4.dp)
//                        .then(
//                            if (dotIndicatorColor != null)
//                                Modifier.background(dotIndicatorColor, CircleShape)
//                            else
//                                Modifier
//                        )
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun CalendarDayCellPreview() {
//    SalatiTheme {
//        CalendarDayCell(
//            gregorianDay = 1,
//            hijriDay = 1,
//            isSelected = true,
//            isCurrentMonth = true,
//            status = DayStatusUi.ALL_PRAYED,
//            onClick = {}
//        )
//    }
//}





//package com.mhq.salati.prayertracker.ui.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
//import com.mhq.salati.shared.ui.theme.SalatiTheme
//
////Original
//@Composable
//fun CalendarDayCell(
//    gregorianDay: Int,
//    hijriDay: Int,
//    isCurrentMonth: Boolean,
//    isFuture: Boolean = false,
//    isSelected: Boolean,
//    status: DayStatusUi?,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val dotIndicatorColor = when (status) {
//        DayStatusUi.ALL_PRAYED -> MaterialTheme.colorScheme.primary
//        DayStatusUi.HAS_MISSED -> MaterialTheme.colorScheme.error
//        DayStatusUi.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
//        else -> null
//    }
//
//    val cellBackgroundColor = when {
//        isSelected -> MaterialTheme.colorScheme.primaryContainer
//        !isCurrentMonth -> MaterialTheme.colorScheme.surfaceVariant
//        else -> MaterialTheme.colorScheme.surface
//    }
//
//    val baseTextColor = MaterialTheme.colorScheme.onSurface
//
//    val cellBorderColor = if (isCurrentMonth && !isFuture) {
//        MaterialTheme.colorScheme.outlineVariant
//    } else {
//        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.15f)
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//            .aspectRatio(1f)
//            .clip(RoundedCornerShape(8.dp))
//            .background(cellBackgroundColor)
//            .border(
//                1.dp,
//                cellBorderColor,
//                RoundedCornerShape(8.dp)
//            )
//            .clickable(enabled = isCurrentMonth && !isFuture, onClick = onClick)
//            .padding(4.dp)
//    ) {
//        Text(
//            text = gregorianDay.toString(),
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Medium,
//            color = when {
//                isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
//                !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.38f)
//                else -> baseTextColor
//            }
//        )
//        Spacer(modifier = Modifier.height(2.dp))
//        Text(
//            text = hijriDay.toString(),
//            fontSize = 13.sp,
//            fontWeight = FontWeight.Light,
//            color = when {
//                isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
//                !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.24f)
//                else -> MaterialTheme.colorScheme.onSurfaceVariant
//            }
//        )
//        Spacer(modifier = Modifier.height(2.dp))
//        Box(
//            modifier = Modifier
//                .size(4.dp)
//                .then(
//                    if (dotIndicatorColor != null)
//                        Modifier.background(dotIndicatorColor, CircleShape)
//                    else
//                        Modifier
//                )
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun CalendarDayCellPreview() {
//    SalatiTheme {
//        CalendarDayCell(
//            gregorianDay = 1,
//            hijriDay = 1,
//            isSelected = true,
//            isCurrentMonth = true,
//            status = DayStatusUi.ALL_PRAYED,
//            onClick = {}
//        )
//    }
//}