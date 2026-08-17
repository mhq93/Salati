package com.mhq.salati.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.domain.HijriMonth
import com.mhq.salati.R
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.shared.presentation.theme.AccentOrange
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.SalatiTheme
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
    val hijriMonthLabel = stringResource(
        HijriMonth.fromNumber(prayerDate.hijriMonthNumber).labelRes
    )

    val gregorianLine = remember(prayerDate.gregorianDate) {
        val parsed = LocalDate.parse(
            prayerDate.gregorianDate,
            DateTimeFormatter.ofPattern("dd-MM-yyyy")
        )
        parsed.format(
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(InkText)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .fillMaxWidth(0.6f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .background(AccentOrange, CircleShape)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onPreviousDay() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.previous_day),
                tint = InkText,
                modifier = Modifier.size(16.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "${prayerDate.hijriDay} $hijriMonthLabel ${prayerDate.hijriYear}",
                color = AccentOrange,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 12.sp,
                    maxFontSize = 20.sp,
                    stepSize = 1.sp
                )
            )
            Text(
                text = gregorianLine,
                color = AccentOrange,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .background(AccentOrange, CircleShape)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onNextDay() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.next_day),
                tint = InkText,
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
                gregorianDate = "17-08-2026",
                hijriDate = "04-03-1448",
                hijriDay = "04",
                hijriMonthNumber = 3,
                hijriYear = "1447"
            ),
            onPreviousDay = {},
            onNextDay = {},
        )
    }
}