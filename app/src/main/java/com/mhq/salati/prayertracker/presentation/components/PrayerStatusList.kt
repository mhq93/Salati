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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.theme.CardBackground
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.Missed
import com.mhq.salati.shared.presentation.theme.MutedSlate
import com.mhq.salati.shared.presentation.theme.Prayed
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import java.time.LocalDate

@Composable
fun PrayerStatusList(
    selectedDate: LocalDate,
    records: Map<PrayerName, PrayerStatus>,
    onPrayerTapped: (PrayerName) -> Unit
) {
    val locked = selectedDate.isAfter(LocalDate.now())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.track_your_prayers),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = InkText,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PrayerName.majorEntries.forEach { prayer ->
                val status = records[prayer] ?: PrayerStatus.PENDING

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardBackground)
                        .clickable(enabled = !locked) { onPrayerTapped(prayer) }
                        .padding(
                            horizontal = 4.dp,
                            vertical = 8.dp
                        ),

                    ) {
                    Text(
                        text = stringResource(prayer.labelRes),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (locked) MutedSlate else InkText,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    when (status) {
                        PrayerStatus.PRAYED -> StatusBadge(
                            icon = Icons.Filled.Check,
                            color = Prayed
                        )

                        PrayerStatus.MISSED -> StatusBadge(
                            icon = Icons.Filled.Close,
                            color = Missed
                        )

                        PrayerStatus.PENDING -> Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color.Transparent)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(MutedSlate.copy(alpha = 0.15f))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrayerStatusListPreview() {
    SalatiTheme() {
        PrayerStatusList(
            selectedDate = LocalDate.now(),
            records = emptyMap(),
            onPrayerTapped = {}
        )
    }
}