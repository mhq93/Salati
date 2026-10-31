package com.mhq.salati.home.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun PrayersList(
    prayers: List<Pair<PrayerNameUi, String>>,
    minorTimings: List<Pair<PrayerNameUi, String>>,
    currentTimingName: PrayerNameUi?,
    mutedTimings: Set<PrayerNameUi>,
    pastTimings: Set<PrayerNameUi>,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        prayers.forEachIndexed { index, (name, time) ->
            val (minorName, minorTime) = minorTimings[index]

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Box(modifier = Modifier.weight(1f)) {
                    PrayerGridItem(
                        prayerName = stringResource(name.labelRes),
                        prayerTime = time,
                        isPrayerHighlighted = name == currentTimingName,
                        isPrayerAdhanMuted = name in mutedTimings,
                        isPrayerPassed = name in pastTimings,
                        onMutePrayerToggle = { onIntent(HomeContract.Intent.ToggleMute(name)) }
                    )
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    PrayerGridItem(
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

@Preview(name = "Light Mode Grid", showBackground = true)
@Preview(name = "Dark Mode Grid", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrayersListPreview() {
    SalatiTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            PrayersList(
                prayers = listOf("Fajr" to "04:30", "Dhuhr" to "12:00", "Asr" to "15:30").map { PrayerNameUi.FAJR to it.second },
                minorTimings = listOf("Imsak" to "04:15", "Shorouq" to "05:45", "Midnight" to "23:30").map { PrayerNameUi.IMSAK to it.second },
                currentTimingName = PrayerNameUi.FAJR,
                mutedTimings = setOf(PrayerNameUi.IMSAK),
                pastTimings = emptySet(),
                onIntent = {}
            )
        }
    }
}