package com.mhq.salati.home.presentation.components

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
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
                MaterialTheme.colorScheme.primaryContainer // <-- Replaced DarkGreenLight
            } else {
                MaterialTheme.colorScheme.surface // <-- Replaced CardBackground
            }
        ),
        border = if (isPrayerHighlighted) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)) // <-- Replaced DarkGreen
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)) // <-- Replaced AccentGreen
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
                    color = if (isPrayerHighlighted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface, // <-- Replaced AccentGold / InkText
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = TextStyle(
                        lineHeight = 19.sp,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both
                        )
                    ),
                    modifier = Modifier.height(19.dp)
                )
                Text(
                    text = prayerTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isPrayerHighlighted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant, // <-- Replaced AccentGold / MutedSage
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = TextStyle(
                        lineHeight = 15.sp,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both
                        )
                    ),
                    modifier = Modifier.height(15.dp)
                )
            }

            IconButton(
                onClick = onMutePrayerToggle,
                enabled = !isPrayerPassed,
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = when {
                            isPrayerHighlighted -> MaterialTheme.colorScheme.tertiary // <-- Replaced AccentGold
                            isPrayerAdhanMuted -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f) // <-- Replaced MutedSage
                            else -> MaterialTheme.colorScheme.secondaryContainer // <-- Already defined as AccentGreen.copy(alpha = 0.12f) in Theme.kt
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
                        isPrayerHighlighted -> MaterialTheme.colorScheme.onPrimaryContainer // <-- Replaced DarkGreenLight
                        isPrayerAdhanMuted -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (isPrayerPassed) 0.4f else 1f) // <-- Replaced MutedSage
                        else -> MaterialTheme.colorScheme.secondary.copy(alpha = if (isPrayerPassed) 0.4f else 1f) // <-- Replaced AccentGreen
                    },
                    contentDescription = if (isPrayerAdhanMuted) "Muted" else "Enabled",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

//package com.mhq.salati.home.presentation.components
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.NotificationsActive
//import androidx.compose.material.icons.filled.NotificationsOff
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.LineHeightStyle
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.shared.domain.PrayerName
//import com.mhq.salati.shared.presentation.theme.AccentGold
//import com.mhq.salati.shared.presentation.theme.AccentGreen
//import com.mhq.salati.shared.presentation.theme.CardBackground
//import com.mhq.salati.shared.presentation.theme.DarkGreen
//import com.mhq.salati.shared.presentation.theme.DarkGreenLight
//import com.mhq.salati.shared.presentation.theme.InkText
//import com.mhq.salati.shared.presentation.theme.MutedSage
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//
//@Composable
//fun PrayerListItem(
//    prayerName: String,
//    prayerTime: String,
//    isPrayerHighlighted: Boolean,
//    isPrayerAdhanMuted: Boolean,
//    isPrayerPassed: Boolean,
//    onMutePrayerToggle: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = if (isPrayerHighlighted) {
//                DarkGreenLight //AccentGree.copy(alpha = 0.08f)
//            } else {
//                CardBackground
//            }
//        ),
//        border = if (isPrayerHighlighted) {
//            BorderStroke(1.dp, DarkGreen.copy(alpha = 0.4f)) //AccentGreen
//        } else {
//            BorderStroke(1.dp, AccentGreen.copy(alpha = 0.4f)) //null
//        },
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(2.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    vertical = 12.dp, // ← kept exactly
//                    horizontal = 16.dp // ← kept exactly
//                )
//        ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(2.dp),
//                modifier = Modifier.weight(1f)
//            ) {
//                Text(
//                    text = prayerName,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 18.sp,
//                    color = if (isPrayerHighlighted) AccentGold else InkText, //AccentGreen
//                    maxLines = 1,
//                    overflow = TextOverflow.Clip,
//                    style = TextStyle(
//                        lineHeight = 19.sp, // ← down from 20.sp
//                        lineHeightStyle = LineHeightStyle(
//                            alignment = LineHeightStyle.Alignment.Center,
//                            trim = LineHeightStyle.Trim.Both
//                        )
//                    ),
//                    modifier = Modifier.height(19.dp) // ← down from 20.dp
//                )
//                Text(
//                    text = prayerTime,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = if (isPrayerHighlighted) AccentGold else MutedSage,
//                    maxLines = 1,
//                    overflow = TextOverflow.Clip,
//                    style = TextStyle(
//                        lineHeight = 15.sp, // ← down from 16.sp
//                        lineHeightStyle = LineHeightStyle(
//                            alignment = LineHeightStyle.Alignment.Center,
//                            trim = LineHeightStyle.Trim.Both
//                        )
//                    ),
//                    modifier = Modifier.height(15.dp) // ← down from 16.dp
//                )
//            }
//
//            IconButton(
//                onClick = onMutePrayerToggle,
//                enabled = !isPrayerPassed,
//                modifier = Modifier
//                    .size(28.dp)
//                    .background(
//                        color = when {
//                            isPrayerHighlighted -> AccentGold
//                            isPrayerAdhanMuted -> MutedSage.copy(alpha = 0.12f)
//                            else -> AccentGreen.copy(alpha = 0.12f)
//                        },
//                        shape = CircleShape
//                    )
//            ) {
//                Icon(
//                    imageVector = if (isPrayerAdhanMuted) {
//                        Icons.Filled.NotificationsOff
//                    } else {
//                        Icons.Filled.NotificationsActive
//                    },
//                    tint = when {
//                        isPrayerHighlighted -> DarkGreenLight
//                        isPrayerAdhanMuted -> MutedSage.copy(alpha = if (isPrayerPassed) 0.4f else 1f)
//                        else -> AccentGreen.copy(alpha = if (isPrayerPassed) 0.4f else 1f)
//                    },
//                    contentDescription = if (isPrayerAdhanMuted) "Muted" else "Enabled",
//                    modifier = Modifier.size(16.dp)
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun PrayerListItemPreview() {
//    SalatiTheme() {
//        PrayerListItem(
//            prayerName = PrayerName.FAJR.name,
//            prayerTime = "04:00",
//            isPrayerHighlighted = true,
//            isPrayerAdhanMuted = true,
//            isPrayerPassed = true,
//                    onMutePrayerToggle = {}
//        )
//    }
//}