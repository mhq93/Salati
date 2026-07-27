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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun PrayersList(
    prayers: List<Triple<String, String, String>>,
    minorTimings: List<Triple<String, String, String>>,
    currentTimingName: String?,
    mutedTimings: Set<String>,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 16.dp)
    ) {
        prayers.forEachIndexed { index, (_, name, time) ->
            val (_, minorName, minorTime) = minorTimings[index]

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    PrayerListItem(
                        prayerName = name,
                        prayerTime = time,
                        isPrayerHighlighted = name == currentTimingName,
                        isPrayerAdhanMuted = name in mutedTimings,
                        onMutePrayerToggle = {
                            onIntent(HomeContract.Intent.ToggleMute(name))
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    PrayerListItem(
                        prayerName = minorName,
                        prayerTime = minorTime,
                        isPrayerHighlighted = minorName == currentTimingName,
                        isPrayerAdhanMuted = minorName in mutedTimings,
                        onMutePrayerToggle = {
                            onIntent(HomeContract.Intent.ToggleMute(minorName))
                        }
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
            onIntent = {}
        )
    }
}