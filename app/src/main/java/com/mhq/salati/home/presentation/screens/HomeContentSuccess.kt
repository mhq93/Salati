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
fun HomeContentSuccessPreview() {
    HomeContentSuccess(
        locationName = "Cairo, Egypt",
        prayerDate = PrayerDate(
            gregorianDate = "",
            hijriDate = "",
            hijriDay = "",
            hijriMonthNumber = 10,
            hijriYear = ""
        ),
        prayerTimings = PrayerTimings(
            fajr = "04:30 AM",
            dhuhr = "12:00 PM",
            asr = "03:30 PM",
            maghrib = "06:45 PM",
            isha = "08:15 PM",
            imsak = "04:15 AM",
            sunrise = "05:45 AM",
            firstThird = "10:00 PM",
            midnight = "11:30 PM",
            lastThird = "02:00 AM",
            sunset = "02:00 AM"
        ),
        remainingMillis = 3600000L,
        nextPrayerInfo = NextPrayerInfo(
            name = PrayerName.FAJR,
            spanStartMillis = 1L,
            spanEndMillis = 1L,
            crossesIntoNextDay = true
        ),
        currentPrayerName = PrayerName.DHUHR,
        mutedPrayers = setOf("FAJR"),
        pastPrayers = setOf(PrayerName.FAJR),
        isToday = true,
        onIntent = {}
    )
}