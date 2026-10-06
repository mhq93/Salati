package com.mhq.salati.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPrayerHighlighted) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isPrayerHighlighted) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f))
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = prayerName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = if (isPrayerHighlighted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Text(
                    text = prayerTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isPrayerHighlighted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            }

            IconButton(
                onClick = onMutePrayerToggle,
                enabled = !isPrayerPassed,
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = when {
                            isPrayerHighlighted -> MaterialTheme.colorScheme.tertiary
                            isPrayerAdhanMuted -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                            else -> MaterialTheme.colorScheme.secondaryContainer
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
                    tint = when {
                        isPrayerHighlighted -> MaterialTheme.colorScheme.onPrimaryContainer
                        isPrayerAdhanMuted -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (isPrayerPassed) 0.4f else 1f)
                        else -> MaterialTheme.colorScheme.secondary.copy(alpha = if (isPrayerPassed) 0.4f else 1f)
                    },
                    contentDescription = if (isPrayerAdhanMuted) "Muted" else "Enabled",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}