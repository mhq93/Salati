package com.mhq.salati.prayertracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.presentation.model.PrayerStatusUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate

@Composable
fun PrayerStatusList(
    selectedDate: LocalDate,
    today: LocalDate,
    duePrayers: Set<PrayerNameUi>,
    records: Map<PrayerNameUi, PrayerStatusUi>,
    onPrayerTapped: (PrayerNameUi) -> Unit
) {
    //val locked = selectedDate.isAfter(today)
    val isFutureDate = selectedDate.isAfter(today)
    val hasUnduePrayers = selectedDate == today && PrayerNameUi.majorEntries.any { it !in duePrayers }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.track_your_prayers),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PrayerNameUi.majorEntries.forEach { prayer ->
                val status = records[prayer] ?: PrayerStatusUi.PENDING
                PrayerStatusCard(
                    prayer = prayer,
                    status = status,
                    //locked = locked,
                    locked = isFutureDate || (selectedDate == today && prayer !in duePrayers),
                    onClick = { onPrayerTapped(prayer) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (hasUnduePrayers) {
            Text(
                text = stringResource(R.string.prayer_marking_opens_at_prayer_time),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PrayerStatusListPreview() {
    SalatiTheme() {
        PrayerStatusList(
            selectedDate = LocalDate.of(2026, 10, 8),
            duePrayers = PrayerNameUi.majorEntries.toSet(),
            today = LocalDate.of(2026, 10, 8),
            records = emptyMap(),
            onPrayerTapped = {}
        )
    }
}