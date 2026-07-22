package com.mhq.salati.presentation.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.domain.model.prayers.PrayerDate
import com.mhq.salati.domain.model.prayers.PrayerTimings
import com.mhq.salati.presentation.home.HomeContract
import com.mhq.salati.presentation.home.components.DateBanner
import com.mhq.salati.presentation.home.components.PrayerArcGauge
import com.mhq.salati.presentation.home.components.PrayersList
import com.mhq.salati.presentation.theme.HeaderGreenDark
import com.mhq.salati.presentation.theme.HeaderGreenLight
import com.mhq.salati.presentation.theme.SalatiTheme
import com.mhq.salati.presentation.theme.SheetBackground
import com.mhq.salati.util.TimeFormatter.parseTimeToMinutes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeSuccessContent(
    prayerDate: PrayerDate,
    prayerTimings: PrayerTimings,
    mutedPrayers: Set<String>,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTimeLabel = remember {
        SimpleDateFormat("hh:mm a", Locale.US).format(Date())
    }
    val nowMinutes = remember(prayerTimings) {
        val now = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        parseTimeToMinutes(now)
    }
    val fajrMinutes = remember(prayerTimings) {
        parseTimeToMinutes(prayerTimings.fajr)
    }
    val ishaMinutes = remember(prayerTimings) {
        parseTimeToMinutes(prayerTimings.isha)
    }

    val prayers = listOf(
        Triple("🌄", "Fajr", prayerTimings.fajr),
        Triple("☀️", "Dhuhr", prayerTimings.dhuhr),
        Triple("🌤️", "Asr", prayerTimings.asr),
        Triple("🌇", "Maghrib", prayerTimings.maghrib),
        Triple("🌙", "Isha", prayerTimings.isha)
    )

    // "Current" prayer = the last one whose time has already passed today.
    val currentPrayerName = remember(prayerTimings) {
        prayers
            .map { it.second to parseTimeToMinutes(it.third) }
            .filter { it.second <= nowMinutes }
            .maxByOrNull { it.second }
            ?.first
    }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(HeaderGreenDark, HeaderGreenLight)
                    ),
                    shape = RoundedCornerShape(
                        bottomStart = 32.dp,
                        bottomEnd = 32.dp
                    )
                )
                .padding(bottom = 24.dp)
        ) {
            DateBanner(
                prayerDate = prayerDate,
                onPreviousDay = { onIntent(HomeContract.Intent.PreviousDay) },
                onNextDay = { onIntent(HomeContract.Intent.NextDay) },
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
            )
            PrayerArcGauge(
                currentTimeLabel = currentTimeLabel,
                fajrLabel = prayerTimings.fajr,
                ishaLabel = prayerTimings.isha,
                fajrMinutes = fajrMinutes,
                ishaMinutes = ishaMinutes,
                nowMinutes = nowMinutes,
                modifier = Modifier.padding(top = 12.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 32.dp,
                        vertical = 8.dp
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Fajr",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = prayerTimings.fajr,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Isha",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = prayerTimings.isha,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp
                    )
                )
                .background(SheetBackground)
                .padding(
                    horizontal = 20.dp,
                    vertical = 20.dp
                )
        ) {
            PrayersList(
                prayers = prayers,
                currentPrayerName = currentPrayerName,
                mutedPrayers = mutedPrayers,
                onIntent = onIntent,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true, name = "Home - Success")
@Composable
private fun HomeContentSuccessPreview() {
    SalatiTheme {
        HomeContent(
            state = HomeContract.State(
                isLoading = false,
                timings = PrayerTimings(
                    fajr = "04:24",
                    sunrise = "06:08",
                    dhuhr = "13:06",
                    asr = "16:46",
                    sunset = "20:04",
                    maghrib = "20:04",
                    isha = "21:36",
                    imsak = "05:11",
                    midnight = "00:12",
                    firstThird = "21:16",
                    lastThird = "03:08"
                ),
                date = PrayerDate(
                    readable = "18 Jul 2026",
                    gregorianDate = "18-07-2026",
                    hijriDate = "03",
                    hijriDay = "03",
                    hijriMonth = "Muharram",
                    hijriYear = "1448"
                )
            ),
            onIntent = {}
        )
    }
}