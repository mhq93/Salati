package com.mhq.salati.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.mhq.salati.domain.model.prayers.PrayerDate
import com.mhq.salati.presentation.theme.AccentGreen
import com.mhq.salati.presentation.theme.InkText
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun DateBanner(
    prayerDate: PrayerDate,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ).fillMaxWidth(0.6f)
    ) {
        IconButton(
            onClick = onPreviousDay,
            modifier = Modifier
                .size(24.dp)
                .background(AccentGreen, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous day",
                tint = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "${prayerDate.hijriDay} ${prayerDate.hijriMonth} ${prayerDate.hijriYear}",
                color = AccentGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = prayerDate.readable,
                color = InkText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        IconButton(
            onClick = onNextDay,
            modifier = Modifier
                .size(24.dp)
                .background(AccentGreen, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next day",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun DateBannerPreview() {
    SalatiTheme() {
        DateBanner(
            prayerDate = PrayerDate(
                readable = "Hi",
                gregorianDate = "Hi",
                hijriDate = "Hi",
                hijriDay = "Hi",
                hijriMonth = "Hi",
                hijriYear = "Hi"
            ),
            onPreviousDay = {},
            onNextDay = {}
        )
    }
}