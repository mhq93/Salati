package com.mhq.salati.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun PrayersList(
    prayers: List<Pair<PrayerName, String>>,
    minorTimings: List<Pair<PrayerName, String>>,
    currentTimingName: PrayerName?,
    mutedTimings: Set<String>,
    pastTimings: Set<PrayerName>,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        prayers.forEachIndexed { index, (name, time) ->
            val (minorName, minorTime) = minorTimings[index]

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Box(modifier = Modifier.weight(1f)) {
                    PrayerListItem(
                        prayerName = stringResource(name.labelRes),
                        prayerTime = time,
                        isPrayerHighlighted = name == currentTimingName,
                        isPrayerAdhanMuted = name.storageKey in mutedTimings,
                        isPrayerPassed = name in pastTimings,
                        onMutePrayerToggle = { onIntent(HomeContract.Intent.ToggleMute(name.storageKey)) }
                    )
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    PrayerListItem(
                        prayerName = stringResource(minorName.labelRes),
                        prayerTime = minorTime,
                        isPrayerHighlighted = minorName == currentTimingName,
                        isPrayerAdhanMuted = minorName.storageKey in mutedTimings,
                        isPrayerPassed = minorName in pastTimings,
                        onMutePrayerToggle = { onIntent(HomeContract.Intent.ToggleMute(minorName.storageKey)) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrayersListPreview() {
    SalatiTheme() {
        PrayersList(
            prayers = emptyList(),
            minorTimings = emptyList(),
            currentTimingName = null,
            mutedTimings = emptySet(),
            pastTimings = emptySet(),
            onIntent = {}
        )
    }
}