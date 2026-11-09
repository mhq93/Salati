package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * One day of the month grid.
 *
 * Font sizes are DERIVED from the cell's own size (not fixed sp values) and use a tight line height
 * with trimmed font padding, so the two numbers always fit inside the cell: narrow phones, large
 * screens, landscape and Arabic digits can no longer push the content out of the cell.
 *
 * - Portrait / tall screens: square cell, Gregorian over Hijri, status dot at the bottom.
 * - Wide screens (landscape, tablets): the cell fills the row height; Gregorian and Hijri share one line.
 */
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
    val isWide = isWideLayout()

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

    val gregorianColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.38f)
        else -> baseTextColor
    }
    val hijriColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        !isCurrentMonth || isFuture -> baseTextColor.copy(alpha = 0.24f)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val cellBorderColor = if (isCurrentMonth && !isFuture) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.15f)
    }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .then(if (isWide) Modifier.fillMaxSize() else Modifier.aspectRatio(1f))
            .clip(RoundedCornerShape(8.dp))
            .background(cellBackgroundColor)
            .border(1.dp, cellBorderColor, RoundedCornerShape(8.dp))
            .clickable(enabled = isCurrentMonth && !isFuture, onClick = onClick)
            .padding(2.dp)
    ) {
        if (isWide) {
            /** Single line: size follows the row height, capped by the width so both numbers fit side by side */
            val gregorianSp = minOf(maxHeight.value * 0.70f, maxWidth.value * 0.32f).coerceIn(9f, 14f)
            val hijriSp = minOf(maxHeight.value * 0.58f, maxWidth.value * 0.26f).coerceIn(8f, 12f)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = gregorianDay.toString(),
                    fontWeight = FontWeight.Medium,
                    color = gregorianColor,
                    style = dayTextStyle(gregorianSp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = hijriDay.toString(),
                    fontWeight = FontWeight.Light,
                    color = hijriColor,
                    style = dayTextStyle(hijriSp)
                )
            }

            /** Status dot pinned to the bottom center of the cell */
            StatusDot(
                color = dotIndicatorColor,
                size = 3.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 3.dp)
            )
        } else {
            /** Both numbers scale with the smaller cell side, so the stack + dot always fit */
            val side = minOf(maxWidth, maxHeight).value
            val gregorianSp = (side * 0.40f).coerceIn(10f, 20f)
            val hijriSp = (side * 0.29f).coerceIn(8f, 14f)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /** Number stack centered in the space above the dot,
                 * so the Hijri number always sits high with clear room below it */
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = gregorianDay.toString(),
                        fontWeight = FontWeight.Medium,
                        color = gregorianColor,
                        style = dayTextStyle(gregorianSp)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = hijriDay.toString(),
                        fontWeight = FontWeight.Light,
                        color = hijriColor,
                        style = dayTextStyle(hijriSp)
                    )
                }

                StatusDot(
                    color = dotIndicatorColor,
                    size = 4.dp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}

/**
 * Tight text style for day numbers: line height is ~1.15x the font size and the extra font
 * padding is trimmed on both sides, so Arabic digits / fonts with tall metrics can't add height.
 */
private fun dayTextStyle(sizeSp: Float) = TextStyle(
    fontSize = sizeSp.sp,
    lineHeight = (sizeSp * 1.15f).sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )
)

/** Reserves its space even when [color] is null so cells without a status keep the same layout. */
@Composable
private fun StatusDot(
    color: androidx.compose.ui.graphics.Color?,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .size(size)
            .then(if (color != null) Modifier.background(color, CircleShape) else Modifier)
    )
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