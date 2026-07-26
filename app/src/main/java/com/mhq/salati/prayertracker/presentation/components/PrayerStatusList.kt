package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.model.PrayerType
import com.mhq.salati.shared.presentation.theme.CardBackground
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.Missed
import com.mhq.salati.shared.presentation.theme.MutedSlate
import com.mhq.salati.shared.presentation.theme.Prayed
import java.time.LocalDate

@Composable
fun PrayerStatusList(
    selectedDate: LocalDate,
    records: Map<PrayerType, PrayerStatus>,
    onPrayerTapped: (PrayerType) -> Unit
) {
    val locked = selectedDate.isAfter(LocalDate.now())
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        PrayerType.entries.forEach { prayer ->
            val status = records[prayer] ?: PrayerStatus.PENDING
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardBackground)
                    .clickable(enabled = !locked) { onPrayerTapped(prayer) }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prayer.displayName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (locked) MutedSlate else InkText
                )
                when (status) {
                    PrayerStatus.PRAYED -> StatusBadge(icon = Icons.Filled.Check, color = Prayed)
                    PrayerStatus.MISSED -> StatusBadge(icon = Icons.Filled.Close, color = Missed)
                    PrayerStatus.PENDING -> Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(MutedSlate.copy(alpha = 0.15f))
                        )
                    }
                }
            }
        }
    }
}

