package com.mhq.salati.alarms.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.text.NumberFormat
import java.time.DayOfWeek
import java.util.Calendar
import java.time.format.TextStyle as DayNameStyle

private val WeekDays = listOf(
    Calendar.MONDAY to DayOfWeek.MONDAY,
    Calendar.TUESDAY to DayOfWeek.TUESDAY,
    Calendar.WEDNESDAY to DayOfWeek.WEDNESDAY,
    Calendar.THURSDAY to DayOfWeek.THURSDAY,
    Calendar.FRIDAY to DayOfWeek.FRIDAY,
    Calendar.SATURDAY to DayOfWeek.SATURDAY,
    Calendar.SUNDAY to DayOfWeek.SUNDAY
)

private val SwitchTrackWidth = 56.dp
private val SwitchTrackHeight = 24.dp
private val SwitchThumbSize = 18.dp
private val SwitchThumbPadding = 3.dp

private data class DayBadge(val calendarDay: Int, val initial: String, val fullName: String)

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
                    .padding(start = 20.dp, top = 8.dp, end = 20.dp)
            ) {
                // ROW 1: Pill on the upper left, Toggle switch on the upper right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f, fill = false)) {
                        if (repeatsEveryDay) {
                            RepeatTag(isEnabled = isEnabled)
                        } else {
                            // Empty box placeholder to balance structure if you want,
                            // or leave blank since SpaceBetween handles alignment
                            Spacer(modifier = Modifier.size(1.dp))
                        }
                    }
                    SlimSwitch(
                        checked = isEnabled,
                        onCheckedChange = onToggle
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // ROW 2: CustomAlarmName · 10 min before Fajr
                OffsetInlineHeadline(
                    customAlarm = customAlarm,
                    prayerName = prayerName,
                    contentAlpha = contentAlpha
                )

                Spacer(modifier = Modifier.height(14.dp))

                // ROW 3: Weekday badging blocks
                val locale = LocalConfiguration.current.locales[0]
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

            // ROW 4: Action Footer Buttons with matching Vertical Divider
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 2.dp)
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
                    modifier = Modifier.fillMaxHeight().padding(vertical = 8.dp)
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
/** New design displaying: CustomAlarmName · 10 min before Fajr inline format */
@Composable
private fun OffsetInlineHeadline(
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
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Composable
private fun RepeatTag(isEnabled: Boolean) {
    val colors = MaterialTheme.colorScheme
    val tint = if (isEnabled) colors.primary else colors.onSurfaceVariant.copy(alpha = 0.6f)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(CircleShape)
            .background(tint.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Repeat,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = stringResource(R.string.repeat_every_day),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = tint,
            maxLines = 1
        )
    }
}
@Composable
private fun SlimSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val trackColor by animateColorAsState(
        targetValue = if (checked) colors.primary else colors.onSurface.copy(alpha = 0.12f),
        label = "switchTrackColor"
    )
    val thumbColor by animateColorAsState(
        targetValue = if (checked) colors.onPrimary else colors.outline,
        label = "switchThumbColor"
    )
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) {
            SwitchTrackWidth - SwitchThumbSize - SwitchThumbPadding
        } else {
            SwitchThumbPadding
        },
        label = "switchThumbOffset"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .minimumInteractiveComponentSize()
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange)
    ) {
        Box(
            modifier = Modifier
                .size(width = SwitchTrackWidth, height = SwitchTrackHeight)
                .clip(CircleShape)
                .background(trackColor)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = thumbOffset)
                    .size(SwitchThumbSize)
                    .clip(CircleShape)
                    .background(thumbColor)
            )
        }
    }
}
@Composable
private fun DayCircle(
    badge: DayBadge,
    isActive: Boolean,
    isAlarmEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val backgroundColor = when {
        !isActive -> colors.onSurface.copy(alpha = 0.05f)
        isAlarmEnabled -> colors.primary.copy(alpha = 0.12f)
        else -> colors.onSurfaceVariant.copy(alpha = 0.1f)
    }
    val textColor = when {
        !isActive -> colors.onSurfaceVariant.copy(alpha = 0.35f)
        isAlarmEnabled -> colors.primary
        else -> colors.onSurfaceVariant.copy(alpha = 0.7f)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .toggleable(value = isActive, role = Role.Checkbox, onValueChange = { onClick() })
            .semantics { contentDescription = badge.fullName }
    ) {
        Text(
            text = badge.initial,
            fontSize = 13.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            modifier = Modifier.clearAndSetSemantics { }
        )
    }
}
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomAlarmCardPreview() {
    SalatiTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CustomAlarmCard(
                customAlarm = CustomAlarmUi(
                    id = 1L,
                    prayerName = PrayerNameUi.FAJR,
                    label = "Morning Devotion",
                    offsetMinutes = 30,
                    offsetDirection = OffsetDirectionUi.BEFORE,
                    everyDay = false,
                    activeDays = setOf(Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY),
                    isEnabled = true
                ),
                onToggle = {}, onEdit = {}, onDelete = {}, onDayToggle = {}
            )
            CustomAlarmCard(
                customAlarm = CustomAlarmUi(
                    id = 2L,
                    prayerName = PrayerNameUi.MAGHRIB,
                    label = "",
                    offsetMinutes = 10,
                    offsetDirection = OffsetDirectionUi.AFTER,
                    everyDay = true,
                    activeDays = emptySet(),
                    isEnabled = true
                ),
                onToggle = {}, onEdit = {}, onDelete = {}, onDayToggle = {}
            )
        }
    }
}