package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.theme.AccentGold
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.DarkGreenLight
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun PrayerTrackerHeader(streak: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(DarkGreenLight, DarkGreen)
                )
            )
            .statusBarsPadding()
            .padding(
                top = 24.dp,
                bottom = 32.dp,
                start = 24.dp,
                end = 24.dp
            )
    ) {
        Text(
            text = stringResource(R.string.prayer_tracker),
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier.height(4.dp)
        )
        Text(
            text = stringResource(R.string.track_your_five_daily_prayers),
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (streak > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = DarkGreenLight,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    )
            ) {
                Icon(
                    Icons.Filled.LocalFireDepartment,
                    contentDescription = null,
                    tint = AccentGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(
                    modifier = Modifier.width(6.dp)
                )
                Text(
                    text = "$streak day streak",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrayerTrackerHeaderPreview() {
    SalatiTheme() {
        PrayerTrackerHeader(
            streak = 0
        )
    }
}