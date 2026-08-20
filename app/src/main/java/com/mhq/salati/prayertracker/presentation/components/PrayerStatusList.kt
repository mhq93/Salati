package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.mhq.salati.shared.presentation.theme.TomatoRed
import com.mhq.salati.shared.presentation.theme.MutedSlate
import com.mhq.salati.shared.presentation.theme.Emerald
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
            fontWeight = FontWeight.SemiBold,
            color = InkText,
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
            selectedDate = LocalDate.now(),
            records = emptyMap(),
            onPrayerTapped = {}
        )
    }
}