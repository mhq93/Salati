package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import java.text.NumberFormat

@Composable
internal fun OffsetInlineHeadline(
    customAlarm: CustomAlarmUi,
    prayerName: String,
    contentAlpha: Float
) {
    val colors = MaterialTheme.colorScheme
    val alarmLabel = customAlarm.label.trim()
        .ifBlank { stringResource(R.string.custom_alarm_default_title) }
    val timingDetails = if (customAlarm.offsetMinutes == 0) {
        stringResource(R.string.at_prayer_time, prayerName)
    } else {
        val offsetText = remember(customAlarm.offsetMinutes) {
            NumberFormat.getInstance().format(customAlarm.offsetMinutes)
        }
        val unitText = stringResource(R.string.minutes_short)
        val directionText = stringResource(
            if (customAlarm.offsetDirection == OffsetDirectionUi.BEFORE) {
                R.string.alarm_offset_before
            } else {
                R.string.alarm_offset_after
            },
            prayerName
        )
        "$offsetText $unitText $directionText"
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$alarmLabel  ·  $timingDetails",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface.copy(alpha = contentAlpha),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}