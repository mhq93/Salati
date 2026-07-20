package com.mhq.salati.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrayersList(
    prayers: List<Triple<String, String, String>>,
    currentPrayerName: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        prayers.forEach { (icon, name, time) ->
            PrayerListItem(
                prayerIcon = icon,
                prayerName = name,
                prayerTime = time,
                isHighlighted = name == currentPrayerName
            )
        }
    }
}