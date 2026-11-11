package com.mhq.salati.alarms.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.shared.ui.labelRes
import java.time.format.TextStyle as DayNameStyle

@Composable
fun CustomAlarmCard(
    customAlarm: CustomAlarmUi,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDayToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val isEnabled = customAlarm.isEnabled
    val contentAlpha = if (isEnabled) 1f else 0.45f
    val configuration = LocalConfiguration.current

    val containerColor by animateColorAsState(
        targetValue = if (isEnabled) colors.surfaceVariant.copy(alpha = 0.4f) else colors.surface,
        label = "cardContainerColor"
    )
    val borderColor = colors.outline.copy(alpha = if (isEnabled) 0.15f else 0.08f)
    val dividerColor = colors.outline.copy(alpha = 0.12f)

    val prayerName = stringResource(customAlarm.prayerName.labelRes)
    val repeatsEveryDay = customAlarm.everyDay ||
            WeekDays.all { (calendarDay, _) -> calendarDay in customAlarm.activeDays }

    Card(
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f, fill = false)) {
                        if (repeatsEveryDay) {
                            RepeatTag(isEnabled = isEnabled)
                        } else {
                            Spacer(modifier = Modifier.size(1.dp))
                        }
                    }
                    Switch(
                        checked = isEnabled,
                        onCheckedChange = onToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.primary,
                            checkedTrackColor = colors.primary.copy(alpha = 0.5f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OffsetInlineHeadline(
                    customAlarm = customAlarm,
                    prayerName = prayerName,
                    contentAlpha = contentAlpha
                )

                Spacer(modifier = Modifier.height(14.dp))

                val locale = configuration.locales[0]
                val badges = remember(locale) {
                    WeekDays.map { (calendarDay, dayOfWeek) ->
                        DayBadge(
                            calendarDay = calendarDay,
                            initial = dayOfWeek.getDisplayName(DayNameStyle.NARROW, locale),
                            fullName = dayOfWeek.getDisplayName(DayNameStyle.FULL, locale)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    badges.forEach { badge ->
                        DayCircle(
                            badge = badge,
                            isActive = customAlarm.everyDay || badge.calendarDay in customAlarm.activeDays,
                            isAlarmEnabled = isEnabled,
                            onClick = { onDayToggle(badge.calendarDay) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            HorizontalDivider(color = dividerColor)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 4.dp)
            ) {
                TextButton(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.edit))
                    }
                }

                VerticalDivider(
                    color = dividerColor,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                )

                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(contentColor = colors.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.delete))
                    }
                }
            }
        }
    }
}