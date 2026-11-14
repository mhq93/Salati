package com.mhq.salati.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.model.PrayerDateUi
import com.mhq.salati.home.presentation.model.PrayerTimingsUi
import com.mhq.salati.home.ui.components.DateBanner
import com.mhq.salati.home.ui.components.LocationHeader
import com.mhq.salati.home.ui.components.PrayerCountdownRing
import com.mhq.salati.home.ui.components.PrayersList
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.formatForDisplay
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.prayerGradient
import java.time.Duration

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
    notificationsEnabled: Boolean = true,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0]
    val isLandscape = isWideLayout()

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

    var headerSizingPx by remember { mutableIntStateOf(0) }
    var bannerHeightPx by remember { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        if (isLandscape) {
            Row(modifier = Modifier.fillMaxSize()) {
                /** Left Column: even 50% split with straight line boundary */
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(brush = MaterialTheme.colorScheme.prayerGradient)
                        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Start))
                        .statusBarsPadding()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    LocationHeader(
                        locationName = locationName,
                        onClick = { onIntent(HomeContract.Intent.LocationPickerRequested) }
                    )
                    PrayerCountdownRing(
                        nextPrayerName = stringResource(nextPrayer.labelRes),
                        remaining = remaining,
                        progress = windowProgress,
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .padding(vertical = 16.dp)
                            .aspectRatio(1f)
                    )
                    DateBanner(
                        prayerDate = prayerDate,
                        isBrowsingToday = isToday,
                        onToday = { onIntent(HomeContract.Intent.TodayRequested) },
                        onPreviousDay = { onIntent(HomeContract.Intent.PreviousDay) },
                        onNextDay = { onIntent(HomeContract.Intent.NextDay) },
                        onEndOfMonth = { onIntent(HomeContract.Intent.EndOfMonthRequested) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                /** Right Column: even 50% split, scrolls when the screen is too short */
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.End))
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    PrayersList(
                        prayers = prayers,
                        minorTimings = minorTimings,
                        currentTimingName = currentPrayerName,
                        mutedTimings = mutedPrayers,
                        pastTimings = pastPrayers,
                        notificationsEnabled = notificationsEnabled,
                        onIntent = onIntent,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { headerSizingPx = it.size.height }
                        .background(
                            brush = MaterialTheme.colorScheme.prayerGradient,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                        .statusBarsPadding()
                        .padding(bottom = 40.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LocationHeader(
                        locationName = locationName,
                        onClick = { onIntent(HomeContract.Intent.LocationPickerRequested) }
                    )
                    PrayerCountdownRing(
                        nextPrayerName = stringResource(nextPrayer.labelRes),
                        remaining = remaining,
                        progress = windowProgress,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .size(220.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    PrayersList(
                        prayers = prayers,
                        minorTimings = minorTimings,
                        currentTimingName = currentPrayerName,
                        mutedTimings = mutedPrayers,
                        pastTimings = pastPrayers,
                        notificationsEnabled = notificationsEnabled,
                        onIntent = onIntent,
                        modifier = Modifier.widthIn(max = 560.dp).fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            DateBanner(
                prayerDate = prayerDate,
                isBrowsingToday = isToday,
                onToday = { onIntent(HomeContract.Intent.TodayRequested) },
                onPreviousDay = { onIntent(HomeContract.Intent.PreviousDay) },
                onNextDay = { onIntent(HomeContract.Intent.NextDay) },
                onEndOfMonth = { onIntent(HomeContract.Intent.EndOfMonthRequested) },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .onGloballyPositioned { bannerHeightPx = it.size.height }
                    .offset { IntOffset(x = 0, y = headerSizingPx - bannerHeightPx / 2) }
            )
        }
    }
}