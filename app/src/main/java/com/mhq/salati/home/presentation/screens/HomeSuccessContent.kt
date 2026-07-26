package com.mhq.salati.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.home.domain.model.PrayerDate
import com.mhq.salati.home.domain.model.PrayerTimings
import com.mhq.salati.home.presentation.components.DateBanner
import com.mhq.salati.home.presentation.components.PrayerArcGauge
import com.mhq.salati.home.presentation.components.PrayersList
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.DarkGreenLight
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.SheetBackground
import com.mhq.salati.shared.utils.TimeFormatter.parseTimeToMinutes
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

    val currentPrayerName = remember(prayerTimings) {
        prayers
            .map { it.second to parseTimeToMinutes(it.third) }
            .filter { it.second <= nowMinutes }
            .maxByOrNull { it.second }
            ?.first
    }

    var headerHeightPx by remember { mutableIntStateOf(0) }
    var bannerHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SheetBackground)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { headerHeightPx = it.size.height }
                    .background(
                        Brush.verticalGradient(
                            listOf(DarkGreen, DarkGreenLight)
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    )
                    .statusBarsPadding()
                    .padding(
                        bottom = 40.dp
                    )
            ) {
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
                            "Fajr",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            prayerTimings.fajr,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Isha",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            prayerTimings.isha,
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
                            topStart = 32.dp,
                            topEnd = 32.dp
                        )
                    )
                    .background(SheetBackground)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {
                Spacer(
                    modifier = Modifier.height(
                        16.dp
                    )
                )

                PrayersList(
                    prayers = prayers,
                    currentPrayerName = currentPrayerName,
                    mutedPrayers = mutedPrayers,
                    onIntent = onIntent,
                    modifier = modifier
                )
            }
        }

        DateBanner(
            prayerDate = prayerDate,
            onPreviousDay = { onIntent(HomeContract.Intent.PreviousDay) },
            onNextDay = { onIntent(HomeContract.Intent.NextDay) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .onGloballyPositioned { bannerHeightPx = it.size.height }
                .offset {
                    IntOffset(
                        x = 0,
                        y = headerHeightPx - bannerHeightPx / 2
                    )
                }
        )
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