package com.mhq.salati.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.domain.model.prayers.PrayerDate
import com.mhq.salati.presentation.theme.InkText

@Composable
fun DateBanner(
    date: PrayerDate,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ){
        Text(
            text = "${date.hijriDate}  ${date.hijriMonthName} ${date.hijriYear}H  ·  ${date.readable}",
            color = InkText,
            fontSize = 12.sp
        )
    }

}