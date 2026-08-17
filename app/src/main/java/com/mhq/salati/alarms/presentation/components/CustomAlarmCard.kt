package com.mhq.salati.alarms.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import java.util.Calendar


@Composable
fun CustomAlarmCard(
    alarm: CustomAlarm,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = if (alarm.isEnabled) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "cardContainerColor"
    )
    val borderColor = if (alarm.isEnabled) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
    }
    val contentAlpha = if (alarm.isEnabled) 1f else 0.45f

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alarm.label.ifBlank { alarm.prayerName },
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha)
                )
                Spacer(Modifier.height(2.dp))

                val offsetText = when {
                    alarm.offsetMinutes == 0 ->
                        stringResource(R.string.at_prayer_time, alarm.prayerName)

                    alarm.offsetDirection == OffsetDirection.BEFORE ->
                        pluralStringResource(
                            R.plurals.minutes_before_prayer,
                            alarm.offsetMinutes,
                            alarm.offsetMinutes,
                            alarm.prayerName
                        )

                    else ->
                        pluralStringResource(
                            R.plurals.minutes_after_prayer,
                            alarm.offsetMinutes,
                            alarm.offsetMinutes,
                            alarm.prayerName
                        )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = offsetText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha * 0.7f)
                    )
                    Text(
                        text = "  •  ${repeatSummary(alarm)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = contentAlpha)
                    )
                }
            }

            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = Color.Transparent,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                ),
                modifier = Modifier.scale(0.85f)
            )

            IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.edit),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.85f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

//@Composable
//fun CustomAlarmCard(
//    alarm: CustomAlarm,
//    onToggle: (Boolean) -> Unit,
//    onEdit: () -> Unit,
//    onDelete: () -> Unit
//) {
//    Card(
//        shape = RoundedCornerShape(20.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = alarm.label.ifBlank { alarm.prayerName },
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//                Spacer(
//                    modifier = Modifier.height(4.dp)
//                )
//                val offsetText = when {
//                    alarm.offsetMinutes == 0 ->
//                        stringResource(
//                            R.string.at_prayer_time,
//                            alarm.prayerName
//                        )
//
//                    alarm.offsetDirection == OffsetDirection.BEFORE ->
//                        pluralStringResource(
//                            R.plurals.minutes_before_prayer,
//                            alarm.offsetMinutes,
//                            alarm.offsetMinutes,
//                            alarm.prayerName
//                        )
//
//                    else ->
//                        pluralStringResource(
//                            R.plurals.minutes_after_prayer,
//                            alarm.offsetMinutes,
//                            alarm.offsetMinutes,
//                            alarm.prayerName
//                        )
//                }
//                Text(
//                    text = offsetText,
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                )
//                Spacer(
//                    modifier = Modifier.height(2.dp)
//                )
//                Text(
//                    text = repeatSummary(alarm),
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.secondary
//                )
//            }
//
//            Switch(
//                checked = alarm.isEnabled,
//                onCheckedChange = onToggle,
//                colors = SwitchDefaults.colors(
//                    checkedThumbColor = MaterialTheme.colorScheme.primary,
//                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
//                )
//            )
//            IconButton(onClick = onEdit) {
//                Icon(
//                    Icons.Default.Edit,
//                    contentDescription = stringResource(R.string.edit),
//                    tint = MaterialTheme.colorScheme.onSurface
//                )
//            }
//            IconButton(onClick = onDelete) {
//                Icon(
//                    Icons.Default.Delete,
//                    contentDescription = stringResource(R.string.delete),
//                    tint = MaterialTheme.colorScheme.error
//                )
//            }
//        }
//    }
//}

@Composable
private fun repeatSummary(alarm: CustomAlarm): String = when {
    alarm.everyDay -> stringResource(R.string.repeats_every_day)
    alarm.activeDays.isEmpty() -> stringResource(R.string.repeats_once)
    else -> alarm.activeDays.sorted().joinToString(", ") { dayShortName(it) }
}

private fun dayShortName(calendarDay: Int): String = when (calendarDay) {
    Calendar.SUNDAY -> "Sun"
    Calendar.MONDAY -> "Mon"
    Calendar.TUESDAY -> "Tue"
    Calendar.WEDNESDAY -> "Wed"
    Calendar.THURSDAY -> "Thu"
    Calendar.FRIDAY -> "Fri"
    Calendar.SATURDAY -> "Sat"
    else -> ""
}