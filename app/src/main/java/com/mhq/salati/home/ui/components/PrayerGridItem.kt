package com.mhq.salati.home.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun PrayerGridItem(
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
                .heightIn(min = 56.dp)
                .padding(horizontal = 14.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = prayerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color =
                        if (isPrayerHighlighted)
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = prayerTime,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color =
                        if (isPrayerHighlighted)
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
                            isPrayerAdhanMuted -> MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.12f
                            )

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
                    contentDescription =
                        if (isPrayerAdhanMuted)
                            stringResource(R.string.muted)
                        else
                            stringResource(R.string.enabled),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(name = "Light Mode - Active", showBackground = true)
@Preview(
    name = "Dark Mode - Active",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PrayerGridItemActivePreview() {
    SalatiTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            PrayerGridItem(
                prayerName = "Asr",
                prayerTime = "15:30",
                isPrayerHighlighted = true,
                isPrayerAdhanMuted = false,
                isPrayerPassed = false,
                onMutePrayerToggle = {}
            )
        }
    }
}

@Preview(name = "Light Mode - Passed & Muted", showBackground = true)
@Preview(
    name = "Dark Mode - Passed & Muted",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PrayerGridItemMutedPreview() {
    SalatiTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            PrayerGridItem(
                prayerName = "Fajr",
                prayerTime = "04:45",
                isPrayerHighlighted = false,
                isPrayerAdhanMuted = true,
                isPrayerPassed = true,
                onMutePrayerToggle = {}
            )
        }
    }
}