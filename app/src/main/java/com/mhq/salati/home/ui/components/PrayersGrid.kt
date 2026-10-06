package com.mhq.salati.home.ui.components

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
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun PrayersList(
    prayers: List<Pair<PrayerName, String>>,
    minorTimings: List<Pair<PrayerName, String>>,
    currentTimingName: PrayerName?,
    mutedTimings: Set<PrayerName>,
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
                        isPrayerAdhanMuted = name in mutedTimings,
                        isPrayerPassed = name in pastTimings,
                        onMutePrayerToggle = { onIntent(HomeContract.Intent.ToggleMute(name)) }
                    )
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    PrayerListItem(
                        prayerName = stringResource(minorName.labelRes),
                        prayerTime = minorTime,
                        isPrayerHighlighted = minorName == currentTimingName,
                        isPrayerAdhanMuted = minorName in mutedTimings,
                        isPrayerPassed = minorName in pastTimings,
                        onMutePrayerToggle = { onIntent(HomeContract.Intent.ToggleMute(minorName)) }
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