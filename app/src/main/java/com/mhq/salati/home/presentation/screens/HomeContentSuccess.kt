package com.mhq.salati.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.home.domain.model.NextPrayerInfo
import com.mhq.salati.home.presentation.components.DateBanner
import com.mhq.salati.home.presentation.components.LocationHeader
import com.mhq.salati.home.presentation.components.PrayerCountdownRing
import com.mhq.salati.home.presentation.components.PrayersList
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.DarkGreenLight
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.SheetBackground

@Composable
fun HomeContentSuccess(
    locationName: String?,
    prayerDate: PrayerDate,
    prayerTimings: PrayerTimings,
    remainingMillis: Long,
    nextPrayerInfo: NextPrayerInfo?,
    currentPrayerName: PrayerName?,
    mutedPrayers: Set<String>,
    pastPrayers: Set<PrayerName>,
    isToday: Boolean,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {

    val prayers = listOf(
        PrayerName.FAJR to prayerTimings.fajr,
        PrayerName.DHUHR to prayerTimings.dhuhr,
        PrayerName.ASR to prayerTimings.asr,
        PrayerName.MAGHRIB to prayerTimings.maghrib,
        PrayerName.ISHA to prayerTimings.isha
    )

    val minorTimings = listOf(
        PrayerName.IMSAK to prayerTimings.imsak,
        PrayerName.SHOROUQ to prayerTimings.sunrise,
        PrayerName.FIRST_THIRD to prayerTimings.firstThird,
        PrayerName.MIDNIGHT to prayerTimings.midnight,
        PrayerName.LAST_THIRD to prayerTimings.lastThird
    )

    var headerHeightPx by remember { mutableIntStateOf(0) }
    var bannerHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
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
                Spacer(
                    modifier = Modifier.height(
                        16.dp
                    )
                )

                LocationHeader(
                    locationName = locationName ?: stringResource(R.string.unknown_location),
                )

                nextPrayerInfo?.let {
                    PrayerCountdownRing(
                        nextPrayerName = stringResource(it.name.labelRes),
                        spanStartMillis = nextPrayerInfo.spanStartMillis,
                        spanEndMillis = nextPrayerInfo.spanEndMillis,
                        remainingMillis = remainingMillis,
                        isToday = isToday,
                        modifier = Modifier.padding(top = 12.dp)
                    )
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
                    minorTimings = minorTimings,
                    currentTimingName = currentPrayerName,
                    mutedTimings = mutedPrayers,
                    pastTimings = pastPrayers,
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

@Preview
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
                    gregorianDate = "18-07-2026",
                    hijriDate = "03",
                    hijriDay = "03",
                    hijriMonthNumber = 3,
                    hijriYear = "1447",
                )
            ),
            onIntent = {}
        )
    }
}