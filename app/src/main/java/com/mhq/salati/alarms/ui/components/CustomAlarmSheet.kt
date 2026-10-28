package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.domain.model.conflictsWith
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.ui.labelRes
import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.format.TextStyle as DayNameStyle
import java.util.Calendar
import java.util.Locale

private val WEEK_DAYS = listOf(
    Calendar.SUNDAY,
    Calendar.MONDAY,
    Calendar.TUESDAY,
    Calendar.WEDNESDAY,
    Calendar.THURSDAY,
    Calendar.FRIDAY,
    Calendar.SATURDAY
)

/** Quick-pick offsets shown under the minutes field. */
private val MINUTE_PRESETS = listOf(5, 10, 15, 20, 30, 45, 60)

private const val MAX_OFFSET_MINUTES = 180
private const val MAX_OFFSET_DIGITS = 3

/**
 * Bottom sheet for creating or editing a custom alarm.
 *
 * @param existingAlarms every saved alarm, used to warn live when the draft would fire at the same
 * time as another alarm (the alarm being edited is ignored).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmSheet(
    existingAlarm: CustomAlarm?,
    existingAlarms: List<CustomAlarm>,
    onSave: (CustomAlarm) -> Unit,
    onDismiss: () -> Unit
) {
    var prayerName by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.prayerName ?: PrayerName.entries.first())
    }
    var label by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.label ?: "")
    }
    // Held as text so the field can be cleared while typing; parsed on demand.
    var minutesText by remember(existingAlarm) {
        mutableStateOf((existingAlarm?.offsetMinutes ?: 5).toString())
    }
    var offsetDirection by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.offsetDirection ?: OffsetDirection.BEFORE)
    }
    var everyDay by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.everyDay ?: true)
    }
    var activeDays by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.activeDays ?: emptySet())
    }
    var isPrayerMenuExpanded by remember { mutableStateOf(false) }

    val offsetMinutes = minutesText.toIntOrNull()
    val hasDays = everyDay || activeDays.isNotEmpty()
    val draft = offsetMinutes?.let {
        CustomAlarm(
            id = existingAlarm?.id ?: 0L,
            prayerName = prayerName,
            label = label.trim(),
            offsetMinutes = it,
            offsetDirection = offsetDirection,
            everyDay = everyDay,
            activeDays = if (everyDay) emptySet() else activeDays,
            isEnabled = existingAlarm?.isEnabled ?: true
        )
    }
    val hasConflict = draft != null && existingAlarms.any {
        it.id != draft.id && it.conflictsWith(draft)
    }
    val canSave = draft != null && hasDays && !hasConflict

    val locale = LocalConfiguration.current.locales[0]
    val numberFormat = remember(locale) { NumberFormat.getInstance(locale) }
    val minutesUnit = stringResource(R.string.minutes_short)

    // Opens fully instead of stopping at half height.
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(
                    if (existingAlarm == null) R.string.new_custom_alarm else R.string.edit_custom_alarm
                ),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            OutlinedTextField(
                value = label,
                onValueChange = { label = it },
                label = { Text(stringResource(R.string.alarm_label_optional)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )

            ExposedDropdownMenuBox(
                expanded = isPrayerMenuExpanded,
                onExpandedChange = { isPrayerMenuExpanded = it }
            ) {
                OutlinedTextField(
                    value = stringResource(prayerName.labelRes),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.prayer)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPrayerMenuExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
                ExposedDropdownMenu(
                    expanded = isPrayerMenuExpanded,
                    onDismissRequest = { isPrayerMenuExpanded = false }
                ) {
                    PrayerName.entries.forEach { name ->
                        DropdownMenuItem(
                            text = { Text(stringResource(name.labelRes)) },
                            onClick = { prayerName = name; isPrayerMenuExpanded = false }
                        )
                    }
                }
            }

            Column {
                Text(
                    stringResource(R.string.timing),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    SegmentedButton(
                        selected = offsetDirection == OffsetDirection.BEFORE,
                        onClick = { offsetDirection = OffsetDirection.BEFORE },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.primary,
                            activeContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) { Text(stringResource(R.string.before)) }
                    SegmentedButton(
                        selected = offsetDirection == OffsetDirection.AFTER,
                        onClick = { offsetDirection = OffsetDirection.AFTER },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.primary,
                            activeContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) { Text(stringResource(R.string.after)) }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Typed minutes (0..180). Digits only; larger numbers snap to the maximum.
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
                    supportingText = {
                        // Inline, inside the sheet: a snackbar would be hidden behind the scrim.
                        if (hasConflict) Text(stringResource(R.string.duplicate_alarm_exists))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(MINUTE_PRESETS) { preset ->
                        FilterChip(
                            selected = offsetMinutes == preset,
                            onClick = { minutesText = preset.toString() },
                            label = { Text("${numberFormat.format(preset)} $minutesUnit") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.repeat_every_day),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Switch(
                        checked = everyDay,
                        onCheckedChange = { everyDay = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    )
                }

                if (!everyDay) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(WEEK_DAYS) { day ->
                            val selected = activeDays.contains(day)
                            FilterChip(
                                selected = selected,
                                onClick = {
                                    activeDays = if (selected) activeDays - day else activeDays + day
                                },
                                label = { Text(dayShortLabel(day, locale)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { draft?.let(onSave) },
                enabled = canSave,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}

/** Localized short weekday name for a [Calendar] day constant (Sunday = 1 ... Saturday = 7). */
private fun dayShortLabel(calendarDay: Int, locale: Locale): String =
    DayOfWeek.of((calendarDay + 5) % 7 + 1).getDisplayName(DayNameStyle.SHORT, locale)