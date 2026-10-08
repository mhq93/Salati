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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.LocalDate

@Composable
fun PrayerStatusList(
    selectedDate: LocalDate,
    today: LocalDate,
    records: Map<PrayerName, PrayerStatus>,
    onPrayerTapped: (PrayerName) -> Unit
) {
    val locked = selectedDate.isAfter(today)

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
            PrayerName.majorEntries.forEach { prayer ->
                val status = records[prayer] ?: PrayerStatus.PENDING
                PrayerStatusCard(
                    prayer = prayer,
                    status = status,
                    locked = locked,
                    onClick = { onPrayerTapped(prayer) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrayerStatusListPreview() {
    SalatiTheme() {
        PrayerStatusList(
            selectedDate = LocalDate.of(2026, 10, 8),
            today = LocalDate.of(2026, 10, 8),
            records = emptyMap(),
            onPrayerTapped = {}
        )
    }
}