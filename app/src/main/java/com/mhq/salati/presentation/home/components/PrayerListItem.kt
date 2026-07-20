package com.mhq.salati.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.presentation.theme.AccentGreen
import com.mhq.salati.presentation.theme.InkText
import com.mhq.salati.presentation.theme.MutedSage
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun PrayerListItem(
    prayerIcon: String,
    prayerName: String,
    prayerTime: String,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {

    var isMuted by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            )
    ) {
        Text(
            text = prayerIcon,
            fontSize = 24.sp,
            modifier = Modifier.padding(
                end = 16.dp
            )
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = prayerName,
                color = if (isHighlighted) AccentGreen else InkText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
            )
            Text(
                text = prayerTime,
                color = MutedSage,
                fontSize = 16.sp
            )
        }

        IconButton(
            onClick = { isMuted = !isMuted }
        ) {
            Icon(
                imageVector =
                    if (isMuted)
                        Icons.Filled.NotificationsOff
                    else
                        Icons.Filled.NotificationsActive,
                contentDescription = if (isMuted) "Muted" else "Enabled",
                tint = if (isMuted) MutedSage else AccentGreen
            )
        }
    }
}

@Preview
@Composable
private fun PrayerListItemPreview() {
    SalatiTheme() {
        PrayerListItem(
            prayerIcon = "Hi",
            prayerName = "Fajr",
            prayerTime = "04:00",
            isHighlighted = true
        )
    }
}