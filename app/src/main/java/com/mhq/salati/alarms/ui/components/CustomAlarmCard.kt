package com.mhq.salati.alarms.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun CustomAlarmCard(
    customAlarm: CustomAlarm,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = if (customAlarm.isEnabled) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "cardContainerColor"
    )

    val borderColor = if (customAlarm.isEnabled) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
    }

    val contentAlpha = if (customAlarm.isEnabled) 1f else 0.45f

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // ✅ SINGLE LINE: Prayer Label + Offset + Toggle + Repeat
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Left: Prayer name and offset
                Text(
                    text = buildAlarmDescription(customAlarm),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                    modifier = Modifier.weight(1f)
                )

                // Center: Toggle switch
                Switch(
                    checked = customAlarm.isEnabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                )

                Spacer(Modifier.width(16.dp))

                // Right: Repeat text
                Text(
                    text = if (customAlarm.everyDay) {
                        stringResource(R.string.repeat_every_day)
                    } else {
                        stringResource(R.string.repeats_once)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable(onClick = onEdit)
                        .padding(vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.edit),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .clickable(onClick = onDelete)
                        .padding(vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.delete),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun buildAlarmDescription(customAlarm: CustomAlarm): String {
    val prayerDisplayName = stringResource(customAlarm.prayerName.labelRes)
    val label = customAlarm.label.ifBlank { prayerDisplayName }

    val offset = when {
        customAlarm.offsetMinutes == 0 ->
            stringResource(R.string.at_prayer_time, prayerDisplayName)

        customAlarm.offsetDirection == OffsetDirection.BEFORE ->
            pluralStringResource(
                R.plurals.minutes_before_prayer,
                customAlarm.offsetMinutes,
                customAlarm.offsetMinutes,
                prayerDisplayName
            )

        else ->
            pluralStringResource(
                R.plurals.minutes_after_prayer,
                customAlarm.offsetMinutes,
                customAlarm.offsetMinutes,
                prayerDisplayName
            )
    }

    return if (offset.isBlank()) label else "$label, $offset"
}

@Preview(showBackground = true)
@Composable
private fun CustomAlarmCardPreview() {
    SalatiTheme {
        CustomAlarmCard(
            customAlarm = CustomAlarm(
                id = 1L,
                prayerName = PrayerName.FAJR,
                label = "Fajr Label",
                offsetMinutes = 10,
                offsetDirection = OffsetDirection.AFTER,
                everyDay = true,
                activeDays = emptySet(),
                isEnabled = true
            ),
            onToggle = {},
            onEdit = {},
            onDelete = {}
        )
    }
}