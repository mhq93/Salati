package com.mhq.salati.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhq.salati.presentation.home.HomeContract

@Composable
fun PrayersList(
    prayers: List<Triple<String, String, String>>,
    currentPrayerName: String?,
    mutedPrayers: Set<String>,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        prayers.forEach { (name, time) ->
            PrayerListItem(
                prayerName = name,
                prayerTime = time,
                isPrayerHighlighted = name == currentPrayerName,
                isPrayerAdhanMuted = name in mutedPrayers,
                onMutePrayerToggle = {
                    onIntent(HomeContract.Intent.ToggleMute(name))
                }
            )
        }
    }
}