package com.mhq.salati.alarms.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.shared.domain.PrayerAlarmNames
import java.util.Calendar

private val WEEK_DAYS = listOf(
    Calendar.SUNDAY,
    Calendar.MONDAY,
    Calendar.TUESDAY,
    Calendar.WEDNESDAY,
    Calendar.THURSDAY,
    Calendar.FRIDAY,
    Calendar.SATURDAY
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmSheet(
    existingAlarm: CustomAlarm?,
    onSave: (CustomAlarm) -> Unit,
    onDismiss: () -> Unit
) {
    var prayerName by remember(existingAlarm) {
        mutableStateOf(
            existingAlarm?.prayerName ?: PrayerAlarmNames.ALL.first()
        )
    }
    var label by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.label ?: "")
    }

    var offsetMinutes by remember(existingAlarm) {
        mutableIntStateOf(
            existingAlarm?.offsetMinutes ?: 5
        )
    }
    var offsetDirection by remember(existingAlarm) {
        mutableStateOf(
            existingAlarm?.offsetDirection ?: OffsetDirection.BEFORE
        )
    }
    var everyDay by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.everyDay ?: true)
    }

    var activeDays by remember(existingAlarm) {
        mutableStateOf(
            existingAlarm?.activeDays ?: emptySet()
        )
    }

    var isPrayerMenuExpanded by remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(if (existingAlarm == null) R.string.new_custom_alarm else R.string.edit_custom_alarm),
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
                onExpandedChange = { isPrayerMenuExpanded = it }) {
                OutlinedTextField(
                    value = prayerName,
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
                    PrayerAlarmNames.ALL.forEach { name ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = { prayerName = name; isPrayerMenuExpanded = false })
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedIconButton(onClick = { if (offsetMinutes > 0) offsetMinutes-- }) {
                        Text("-", style = MaterialTheme.typography.titleLarge)
                    }
                    Text(
                        text = stringResource(R.string.minutes_value, offsetMinutes),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    OutlinedIconButton(onClick = { if (offsetMinutes < 180) offsetMinutes++ }) {
                        Text("+", style = MaterialTheme.typography.titleLarge)
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
                                    activeDays =
                                        if (selected) activeDays - day else activeDays + day
                                },
                                label = { Text(dayShortLabel(day)) },
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
                onClick = {
                    onSave(
                        CustomAlarm(
                            id = existingAlarm?.id ?: 0L,
                            prayerName = prayerName,
                            label = label,
                            offsetMinutes = offsetMinutes,
                            offsetDirection = offsetDirection,
                            everyDay = everyDay,
                            activeDays = if (everyDay) emptySet() else activeDays,
                            isEnabled = existingAlarm?.isEnabled ?: true
                        )
                    )
                },
                enabled = everyDay || activeDays.isNotEmpty(),
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

private fun dayShortLabel(calendarDay: Int): String = when (calendarDay) {
    Calendar.SUNDAY -> "Sun"
    Calendar.MONDAY -> "Mon"
    Calendar.TUESDAY -> "Tue"
    Calendar.WEDNESDAY -> "Wed"
    Calendar.THURSDAY -> "Thu"
    Calendar.FRIDAY -> "Fri"
    Calendar.SATURDAY -> "Sat"
    else -> ""
}