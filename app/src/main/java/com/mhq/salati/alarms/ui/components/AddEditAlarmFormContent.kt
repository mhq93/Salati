package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.mapper.conflictsWith
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.alarms.ui.state.AlarmFormState
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.format.TextStyle as DayNameStyle

private const val MAX_OFFSET_MINUTES = 180
private const val MAX_OFFSET_DIGITS = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmFormContent(
    formState: AlarmFormState,
    existingAlarm: CustomAlarmUi?,
    existingAlarms: List<CustomAlarmUi>,
    onSave: (CustomAlarmUi) -> Unit,
    onDismiss: () -> Unit
) {
    var prayerName by formState.prayerName
    var label by formState.label
    var minutesText by formState.minutesText
    var offsetDirection by formState.offsetDirection
    var activeDays by formState.activeDays
    var hasSaved by remember { mutableStateOf(false) }
    var isPrayerMenuExpanded by remember { mutableStateOf(false) }

    val offsetMinutes = minutesText.toIntOrNull()
    val fullWeekSelected = activeDays.size == 7
    val draft = offsetMinutes?.let {
        CustomAlarmUi(
            id = existingAlarm?.id ?: 0L,
            prayerName = prayerName,
            label = label.trim(),
            offsetMinutes = it,
            offsetDirection = offsetDirection,
            everyDay = fullWeekSelected,
            activeDays = if (fullWeekSelected) emptySet() else activeDays,
            isEnabled = existingAlarm?.isEnabled ?: true
        )
    }
    val hasConflict = !hasSaved && draft != null && existingAlarms.any {
        it.id != draft.id && it.conflictsWith(draft)
    }
    val canSave = draft != null && activeDays.isNotEmpty() && !hasConflict

    val configuration = LocalConfiguration.current
    val primaryLocale = configuration.locales
    val isLandscape = isWideLayout()
    val minutesUnit = stringResource(R.string.minutes_short)

    Column(
        modifier = Modifier
            .then(if (isLandscape) Modifier.fillMaxHeight() else Modifier.wrapContentHeight())
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AlarmTitleEditor(
            label = label,
            onLabelChange = { label = it }
        )

        // ROW 1: [Minutes Input] (Left) | [Before/After Vertical Stack] (Center) | [Prayer Dropdown] (Right)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Left Element: Minutes Input
            OutlinedTextField(
                value = minutesText,
                onValueChange = { input ->
                    val digits = input.filter { it.isDigit() }.take(MAX_OFFSET_DIGITS)
                    val value = digits.toIntOrNull()
                    minutesText = if (value != null && value > MAX_OFFSET_MINUTES) {
                        MAX_OFFSET_MINUTES.toString()
                    } else {
                        digits
                    }
                },
                label = { Text(stringResource(R.string.alarm_minutes_field_label)) },
                suffix = { Text(minutesUnit) },
                singleLine = true,
                isError = hasConflict,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .height(OutlinedTextFieldDefaults.MinHeight)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        RoundedCornerShape(8.dp)
                    )
                    .selectableGroup()
            ) {
                OffsetDirectionSegment(
                    text = stringResource(R.string.before),
                    selected = offsetDirection == OffsetDirectionUi.BEFORE,
                    onClick = { offsetDirection = OffsetDirectionUi.BEFORE },
                    modifier = Modifier.weight(1f)
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                OffsetDirectionSegment(
                    text = stringResource(R.string.after),
                    selected = offsetDirection == OffsetDirectionUi.AFTER,
                    onClick = { offsetDirection = OffsetDirectionUi.AFTER },
                    modifier = Modifier.weight(1f)
                )
            }
            // Right Element: Prayer Dropdown Selection
            ExposedDropdownMenuBox(
                expanded = isPrayerMenuExpanded,
                onExpandedChange = { isPrayerMenuExpanded = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = stringResource(prayerName.labelRes),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.prayer)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPrayerMenuExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
                ExposedDropdownMenu(
                    expanded = isPrayerMenuExpanded,
                    onDismissRequest = { isPrayerMenuExpanded = false }
                ) {
                    PrayerNameUi.entries.forEach { name ->
                        DropdownMenuItem(
                            text = { Text(stringResource(name.labelRes)) },
                            onClick = { prayerName = name; isPrayerMenuExpanded = false }
                        )
                    }
                }
            }
        }

        if (hasConflict) {
            Text(
                text = stringResource(R.string.duplicate_alarm_exists),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        // ROW 2: Balanced 7-day circles line
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.repeat_every_day),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            val primaryLocaleInstance =
                primaryLocale[0] // Extracts a single stable Locale from the list container

            val badges = remember(primaryLocaleInstance) {
                WeekDays.map { (calendarDay, dayOfWeek) ->
                    DayBadge(
                        calendarDay = calendarDay,
                        initial = dayOfWeek.getDisplayName(
                            DayNameStyle.NARROW,
                            primaryLocaleInstance
                        ),
                        fullName = dayOfWeek.getDisplayName(
                            DayNameStyle.FULL,
                            primaryLocaleInstance
                        )
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()
            ) {
                badges.forEach { badge ->
                    val isDayActive = activeDays.contains(badge.calendarDay)
                    val bgCircleColor = if (isDayActive) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                    }
                    val txtCircleColor = if (isDayActive) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(bgCircleColor)
                            .toggleable(
                                value = isDayActive,
                                role = Role.Checkbox,
                                onValueChange = {
                                    activeDays = if (isDayActive) {
                                        activeDays - badge.calendarDay
                                    } else {
                                        activeDays + badge.calendarDay
                                    }
                                }
                            )
                            .semantics { contentDescription = badge.fullName }
                    ) {
                        Text(
                            text = badge.initial,
                            fontSize = 13.sp,
                            fontWeight = if (isDayActive) FontWeight.Bold else FontWeight.Normal,
                            color = txtCircleColor,
                            modifier = Modifier.clearAndSetSemantics { }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        // Actions Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLandscape) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.cd_back))
                }
            }
            Button(
                onClick = { hasSaved = true; draft?.let(onSave) },
                enabled = canSave && !hasSaved,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.weight(if (isLandscape) 1f else 2f)
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}

@Preview(name = "Form - Portrait", showBackground = true)
@Preview(
    name = "Form - Landscape",
    device = "spec:width=800dp,height=400dp,dpi=440",
    showBackground = true
)
@Composable
private fun AddEditAlarmFormContentPreview() {
    SalatiTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            AddEditAlarmFormContent(
                formState = remember { AlarmFormState(null) },
                existingAlarm = null,
                existingAlarms = emptyList(),
                onSave = {},
                onDismiss = {}
            )
        }
    }
}