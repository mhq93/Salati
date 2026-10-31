package com.mhq.salati.alarms.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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

/** Week order shown on the card, paired with the [Calendar] constants stored in the alarm. */
private val WeekDays = listOf(
    Calendar.MONDAY to DayOfWeek.MONDAY,
    Calendar.TUESDAY to DayOfWeek.TUESDAY,
    Calendar.WEDNESDAY to DayOfWeek.WEDNESDAY,
    Calendar.THURSDAY to DayOfWeek.THURSDAY,
    Calendar.FRIDAY to DayOfWeek.FRIDAY,
    Calendar.SATURDAY to DayOfWeek.SATURDAY,
    Calendar.SUNDAY to DayOfWeek.SUNDAY
)

// Slim switch geometry: thinner and longer than the Material 3 default (52 x 32 dp).
private val SwitchTrackWidth = 56.dp
private val SwitchTrackHeight = 24.dp
private val SwitchThumbSize = 18.dp
private val SwitchThumbPadding = 3.dp

/** A day circle's [Calendar] constant plus its localized one-letter and full names. */
private data class DayBadge(val calendarDay: Int, val initial: String, val fullName: String)

/**
 * Card showing one custom alarm: label (or a fallback) with an "every day" tag, the offset from
 * the prayer, tappable day circles, and separate Edit / Delete actions.
 *
 * @param onDayToggle called with a [Calendar] day constant when the user taps a day circle.
 */
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
    val accentColor =
        if (isEnabled) colors.primary else colors.onSurfaceVariant.copy(alpha = 0.6f)

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
                // Header: label + "every day" tag, offset headline, and the switch.
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = customAlarm.label.trim()
                                    .ifBlank { stringResource(R.string.custom_alarm_label_fallback) },
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            if (repeatsEveryDay) {
                                RepeatTag(isEnabled = isEnabled)
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        OffsetHeadline(
                            customAlarm = customAlarm,
                            prayerName = prayerName,
                            contentAlpha = contentAlpha
                        )
                    }

                    SlimSwitch(
                        checked = isEnabled,
                        onCheckedChange = onToggle
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Day circles: tap to include or exclude a weekday.
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
                            isActive = customAlarm.everyDay ||
                                    badge.calendarDay in customAlarm.activeDays,
                            isAlarmEnabled = isEnabled,
                            onClick = { onDayToggle(badge.calendarDay) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            HorizontalDivider(color = colors.outline.copy(alpha = 0.12f))

            // Actions sit at opposite ends so a tap meant for Edit never lands on Delete.
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                TextButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.edit))
                }
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(contentColor = colors.error)
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

/** Big offset number with its unit and "before/after <prayer>", or "At <prayer>" for a zero offset. */
@Composable
private fun OffsetHeadline(
    customAlarm: CustomAlarmUi,
    prayerName: String,
    contentAlpha: Float
) {
    val colors = MaterialTheme.colorScheme

    if (customAlarm.offsetMinutes == 0) {
        Text(
            text = stringResource(R.string.at_prayer_time, prayerName),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface.copy(alpha = contentAlpha)
        )
        return
    }

    val offsetText = remember(customAlarm.offsetMinutes) {
        NumberFormat.getInstance().format(customAlarm.offsetMinutes)
    }
    val directionText = stringResource(
        if (customAlarm.offsetDirection == OffsetDirectionUi.BEFORE) {
            R.string.alarm_offset_before
        } else {
            R.string.alarm_offset_after
        },
        prayerName
    )

    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = offsetText,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface.copy(alpha = contentAlpha)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.minutes_short),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = colors.onSurfaceVariant.copy(alpha = contentAlpha),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = directionText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = colors.onSurfaceVariant.copy(alpha = contentAlpha),
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 6.dp)
        )
    }
}

/** Small "Repeat · Every day" pill shown next to the label. */
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
            .padding(horizontal = 8.dp, vertical = 3.dp)
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
            color = tint,
            maxLines = 1
        )
    }
}

/** Slimmer and longer replacement for the Material 3 switch; keeps a 48 dp touch target. */
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

/** One tappable weekday circle; announces the full day name to accessibility services. */
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
            // With label, selected days
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
            // No label (fallback), every day
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
            // No label, disabled, zero offset
            CustomAlarmCard(
                customAlarm = CustomAlarmUi(
                    id = 3L,
                    prayerName = PrayerNameUi.ISHA,
                    label = "",
                    offsetMinutes = 0,
                    offsetDirection = OffsetDirectionUi.BEFORE,
                    everyDay = false,
                    activeDays = setOf(Calendar.SATURDAY, Calendar.SUNDAY),
                    isEnabled = false
                ),
                onToggle = {}, onEdit = {}, onDelete = {}, onDayToggle = {}
            )
        }
    }
}