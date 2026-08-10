package com.mhq.salati.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.mhq.salati.shared.presentation.theme.AccentGreen
import com.mhq.salati.shared.presentation.theme.CardBackground
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.MutedSage
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun PrayerListItem(
    prayerName: String,
    prayerTime: String,
    isPrayerHighlighted: Boolean,
    isPrayerAdhanMuted: Boolean,
    isPrayerPassed: Boolean,
    onMutePrayerToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPrayerHighlighted) {
                AccentGreen.copy(alpha = 0.08f)
            } else {
                CardBackground
            }
        ),
        border = if (isPrayerHighlighted) {
            BorderStroke(1.dp, AccentGreen.copy(alpha = 0.4f))
        } else {
            null
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = prayerName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = if (isPrayerHighlighted) AccentGreen else InkText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = prayerTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MutedSage
                )
            }

            IconButton(
                onClick = onMutePrayerToggle,
                enabled = !isPrayerPassed,
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = if (isPrayerAdhanMuted) {
                            MutedSage.copy(alpha = 0.12f)
                        } else {
                            AccentGreen.copy(alpha = 0.12f)
                        },
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isPrayerAdhanMuted) {
                        Icons.Filled.NotificationsOff
                    } else {
                        Icons.Filled.NotificationsActive
                    },
                    tint = (if (isPrayerAdhanMuted) MutedSage else AccentGreen)
                        .copy(alpha = if (isPrayerPassed) 0.4f else 1f),
                    contentDescription = if (isPrayerAdhanMuted) "Muted" else "Enabled",
                    modifier = Modifier.size(18.dp)
                )
            }
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
            isPrayerPassed = true,
                    onMutePrayerToggle = {}
        )
    }
}