package com.mhq.salati.home.ui.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.model.HijriDateUi
import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.home.ui.components.DateBanner
import com.mhq.salati.home.ui.components.LocationHeader
import com.mhq.salati.home.ui.components.PrayerCountdownRing
import com.mhq.salati.home.ui.components.PrayersList
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.HijriMonthUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.formatForDisplay
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.prayerGradient
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

/** Prayer times are shown as plain 24h "HH:mm" with ASCII digits (same as the raw API string was).
private val PrayerTimeDisplayFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US) */

@Composable
fun HomeContentSuccess(
    locationName: String?,
    prayerDate: PrayerDateUi,
    prayerTimings: PrayerTimingsUi,
    nextPrayer: PrayerNameUi,
    remaining: Duration,
    windowProgress: Float,
    currentPrayerName: PrayerNameUi?,
    mutedPrayers: Set<PrayerNameUi>,
    pastPrayers: Set<PrayerNameUi>,
    isToday: Boolean,
    timeFormat: TimeFormatUi = TimeFormatUi.FORMAT_12_HOUR,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    val locale = LocalConfiguration.current.locales[0]

    val prayers = remember(prayerTimings, timeFormat, locale) {
        listOf(
            PrayerNameUi.FAJR,
            PrayerNameUi.DHUHR,
            PrayerNameUi.ASR,
            PrayerNameUi.MAGHRIB,
            PrayerNameUi.ISHA
        ).map { name -> name to prayerTimings[name].formatForDisplay(timeFormat, locale) }
    }

    val minorTimings = remember(prayerTimings, timeFormat, locale) {
        listOf(
            PrayerNameUi.IMSAK,
            PrayerNameUi.SHOROUQ,
            PrayerNameUi.FIRST_THIRD,
            PrayerNameUi.MIDNIGHT,
            PrayerNameUi.LAST_THIRD
        ).map { name -> name to prayerTimings[name].formatForDisplay(timeFormat, locale) }
    }

    var headerHeightPx by remember { mutableIntStateOf(0) }
    var bannerHeightPx by remember { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { headerHeightPx = it.size.height }
                    .background(
                        brush = MaterialTheme.colorScheme.prayerGradient,
                        shape = RoundedCornerShape(
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    )
                    .statusBarsPadding()
                    .padding(bottom = 40.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                LocationHeader(
                    locationName = locationName ?: stringResource(R.string.unknown_location),
                )

                PrayerCountdownRing(
                    nextPrayerName = stringResource(nextPrayer.labelRes),
                    remaining = remaining,
                    // The sweep only makes sense for today's real countdown.
                    progress = if (isToday) windowProgress else 0f,
                    modifier = Modifier.padding(top = 12.dp)
                )
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
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
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

@Preview(name = "Light Mode - Success", showSystemUi = true)
@Preview(
    name = "Dark Mode - Success",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true
)
@Composable
private fun HomeContentSuccessPreview() {
    SalatiTheme {
        HomeContent(
            state = HomeContract.State(
                isLoading = false,
                errorMessage = null,
                location = HomeContract.LocationUiState(locationName = "Cairo, Egypt"),
                dateBrowser = HomeContract.DateBrowserUiState(isBrowsingToday = true),
                prayerTimes = HomeContract.PrayerTimesUiState(
                    date = PrayerDateUi(
                        gregorianDate = LocalDate.of(2026, 8, 17),
                        hijriDate = HijriDateUi(4, HijriMonthUi.SHAWWAL, 1448)
                    ),
                    timings = PrayerTimingsUi(
                        mapOf(
                            PrayerNameUi.IMSAK to LocalTime.of(4, 15),
                            PrayerNameUi.FAJR to LocalTime.of(4, 30),
                            PrayerNameUi.SHOROUQ to LocalTime.of(5, 45),
                            PrayerNameUi.DHUHR to LocalTime.of(12, 0),
                            PrayerNameUi.ASR to LocalTime.of(15, 30),
                            PrayerNameUi.MAGHRIB to LocalTime.of(18, 45),
                            PrayerNameUi.ISHA to LocalTime.of(20, 15),
                            PrayerNameUi.MIDNIGHT to LocalTime.of(23, 30),
                            PrayerNameUi.FIRST_THIRD to LocalTime.of(22, 0),
                            PrayerNameUi.LAST_THIRD to LocalTime.of(2, 0)
                        )
                    ),
                    nextPrayerName = PrayerNameUi.FAJR,
                    remaining = Duration.ofMinutes(45),
                    windowProgress = 0.6f
                )
            ),
            onIntent = {}
        )
    }
}