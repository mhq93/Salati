package com.mhq.salati.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertimes.domain.model.HijriDate
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.shared.domain.HijriMonth
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateBanner(
    prayerDate: PrayerDate,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hijriMonthLabel = stringResource(prayerDate.hijriDate.month.labelRes)

    // Display formatter: intentionally follows the current app locale (re-created when the date changes).
    val gregorianLine = remember(prayerDate.gregorianDate) {
        prayerDate.gregorianDate.format(
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(0.6f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    CircleShape
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onPreviousDay() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.previous_day),
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(16.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "${prayerDate.hijriDate.day} $hijriMonthLabel ${prayerDate.hijriDate.year}",
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 12.sp,
                    maxFontSize = 20.sp,
                    stepSize = 1.sp
                ),
                style = TextStyle(
                    lineHeight = 22.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                ),
                modifier = Modifier.height(22.dp)
            )
            Text(
                text = gregorianLine,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = TextStyle(
                    lineHeight = 18.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                ),
                modifier = Modifier.height(18.dp)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    CircleShape
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onNextDay() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.next_day),
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateBannerPreview() {
    SalatiTheme() {
        DateBanner(
            prayerDate = PrayerDate(
                gregorianDate = LocalDate.of(2026, 8, 17),
                hijriDate = HijriDate(day = 4, month = HijriMonth.RABI_AL_AWWAL, year = 1448)
            ),
            onPreviousDay = {},
            onNextDay = {}
        )
    }
}