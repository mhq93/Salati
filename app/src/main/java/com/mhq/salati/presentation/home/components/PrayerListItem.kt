package com.mhq.salati.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.presentation.theme.AccentGreen
import com.mhq.salati.presentation.theme.InkText
import com.mhq.salati.presentation.theme.MutedSage
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun PrayerListItem(
    prayerName: String,
    prayerTime: String,
    isPrayerHighlighted: Boolean,
    isPrayerAdhanMuted: Boolean,
    onMutePrayerToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = prayerName,
                color = if (isPrayerHighlighted) AccentGreen else InkText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = prayerTime,
                color = MutedSage,
                fontSize = 16.sp
            )
        }

        IconButton(
            onClick = onMutePrayerToggle,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector =
                    if (isPrayerAdhanMuted)
                        Icons.Filled.NotificationsOff
                    else
                        Icons.Filled.NotificationsActive,
                contentDescription =
                    if (isPrayerAdhanMuted)
                        "Muted"
                    else
                        "Enabled",
                tint =
                    if (isPrayerAdhanMuted)
                        MutedSage
                    else
                        AccentGreen
            )
        }
    }
}

@Preview
@Composable
private fun PrayerListItemPreview() {
    SalatiTheme() {
        PrayerListItem(
            prayerName = "Fajr",
            prayerTime = "04:00",
            isPrayerHighlighted = true,
            isPrayerAdhanMuted = true,
            onMutePrayerToggle = {},
        )
    }
}