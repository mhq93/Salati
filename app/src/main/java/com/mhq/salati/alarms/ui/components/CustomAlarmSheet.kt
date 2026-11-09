package com.mhq.salati.alarms.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.ui.labelRes
import com.mhq.salati.shared.ui.theme.SalatiTheme
import java.time.DayOfWeek
import java.util.Calendar
import java.time.format.TextStyle as DayNameStyle

private val WEEK_DAYS = listOf(
    Calendar.MONDAY to DayOfWeek.MONDAY,
    Calendar.TUESDAY to DayOfWeek.TUESDAY,
    Calendar.WEDNESDAY to DayOfWeek.WEDNESDAY,
    Calendar.THURSDAY to DayOfWeek.THURSDAY,
    Calendar.FRIDAY to DayOfWeek.FRIDAY,
    Calendar.SATURDAY to DayOfWeek.SATURDAY,
    Calendar.SUNDAY to DayOfWeek.SUNDAY
)
private const val MAX_OFFSET_MINUTES = 180
private const val MAX_OFFSET_DIGITS = 3

private data class SheetDayBadge(val calendarDay: Int, val initial: String, val fullName: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmSheet(
    existingAlarm: CustomAlarmUi?,
    existingAlarms: List<CustomAlarmUi>,
    onSave: (CustomAlarmUi) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .imePadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            AddEditAlarmFormContent(
                existingAlarm = existingAlarm,
                existingAlarms = existingAlarms,
                onSave = onSave,
                onDismiss = onDismiss
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmFormContent(
    existingAlarm: CustomAlarmUi?,
    existingAlarms: List<CustomAlarmUi>,
    onSave: (CustomAlarmUi) -> Unit,
    onDismiss: () -> Unit
) {
    var prayerName by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.prayerName ?: PrayerNameUi.entries.first())
    }
    var label by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.label ?: "")
    }
    var minutesText by remember(existingAlarm) {
        mutableStateOf((existingAlarm?.offsetMinutes ?: 5).toString())
    }
    var offsetDirection by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.offsetDirection ?: OffsetDirectionUi.BEFORE)
    }
    // Highlighting all days by default if creating a new alarm
    var activeDays by remember(existingAlarm) {
        mutableStateOf(existingAlarm?.activeDays ?: WEEK_DAYS.map { it.first }.toSet())
    }
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
    val hasConflict = draft != null && existingAlarms.any {
        it.id != draft.id && it.conflictsWith(draft)
    }
    val canSave = draft != null && activeDays.isNotEmpty() && !hasConflict

    val configuration = LocalConfiguration.current
    val primaryLocale = configuration.locales
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val minutesUnit = stringResource(R.string.minutes_short)

    Column(
        modifier = Modifier
            .then(if (isLandscape) Modifier.fillMaxHeight() else Modifier.wrapContentHeight())
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(if (existingAlarm == null) R.string.new_custom_alarm else R.string.edit_custom_alarm),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
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

        // ROW 1: [Minutes Input] (Left) | [Before/After Vertical Stack] (Center) | [Prayer Dropdown] (Right)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min), // 1. Measures the text fields and forces the row to match their height
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
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
                supportingText = {
                    if (hasConflict) Text(stringResource(R.string.duplicate_alarm_exists))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1.2f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )

            // Center Element: Stacked vertical Before/After container matching text field heights exactly
            Column(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxHeight() // 2. Tells the column to stretch completely to the measured height of the row
                    .padding(bottom = if (hasConflict) 16.dp else 0.dp) // Accounts for supporting text displacement
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                verticalArrangement = Arrangement.Center
            ) {
                val activeBg = MaterialTheme.colorScheme.primary
                val activeTxt = MaterialTheme.colorScheme.onPrimary
                val inactiveTxt = MaterialTheme.colorScheme.onSurfaceVariant

                // Before Button (Top Half)
                androidx.compose.material3.TextButton(
                    onClick = { offsetDirection = OffsetDirectionUi.BEFORE },
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (offsetDirection == OffsetDirectionUi.BEFORE) activeBg else androidx.compose.ui.graphics.Color.Transparent,
                        contentColor = if (offsetDirection == OffsetDirectionUi.BEFORE) activeTxt else inactiveTxt
                    ),
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(0.dp)
                ) {
                    Text(stringResource(R.string.before), fontSize = 11.sp, fontWeight = FontWeight.Medium, maxLines = 1)
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)

                // After Button (Bottom Half)
                androidx.compose.material3.TextButton(
                    onClick = { offsetDirection = OffsetDirectionUi.AFTER },
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (offsetDirection == OffsetDirectionUi.AFTER) activeBg else androidx.compose.ui.graphics.Color.Transparent,
                        contentColor = if (offsetDirection == OffsetDirectionUi.AFTER) activeTxt else inactiveTxt
                    ),
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(0.dp)
                ) {
                    Text(stringResource(R.string.after), fontSize = 11.sp, fontWeight = FontWeight.Medium, maxLines = 1)
                }
            }

            // Right Element: Prayer Dropdown Selection
            ExposedDropdownMenuBox(
                expanded = isPrayerMenuExpanded,
                onExpandedChange = { isPrayerMenuExpanded = it },
                modifier = Modifier
                    .weight(1.3f)
                    .padding(bottom = if (hasConflict) 16.dp else 0.dp)
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

// ROW 2: Balanced 7 day circles line
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.repeat_every_day),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            val primaryLocaleInstance = primaryLocale[0] // Extracts a single stable Locale from the list container

            val badges = remember(primaryLocaleInstance) {
                WEEK_DAYS.map { (calendarDay, dayOfWeek) ->
                    SheetDayBadge(
                        calendarDay = calendarDay,
                        initial = dayOfWeek.getDisplayName(DayNameStyle.NARROW, primaryLocaleInstance),
                        fullName = dayOfWeek.getDisplayName(DayNameStyle.FULL, primaryLocaleInstance)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(if (isLandscape) 0.6f else 1f) // Keep compact in landscape splits
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
                onClick = { draft?.let(onSave) },
                enabled = canSave,
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
                existingAlarm = null,
                existingAlarms = emptyList(),
                onSave = {},
                onDismiss = {}
            )
        }
    }
}

//package com.mhq.salati.alarms.ui.components
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.FilterChip
//import androidx.compose.material3.FilterChipDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.SegmentedButton
//import androidx.compose.material3.SegmentedButtonDefaults
//import androidx.compose.material3.SingleChoiceSegmentedButtonRow
//import androidx.compose.material3.Switch
//import androidx.compose.material3.SwitchDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import com.mhq.salati.R
//import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
//import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
//import com.mhq.salati.alarms.presentation.mapper.conflictsWith
//import com.mhq.salati.shared.presentation.model.PrayerNameUi
//import com.mhq.salati.shared.ui.labelRes
//import java.text.NumberFormat
//import java.time.DayOfWeek
//import java.time.format.TextStyle as DayNameStyle
//import java.util.Calendar
//import java.util.Locale
//
//private val WEEK_DAYS = listOf(
//    Calendar.SUNDAY,
//    Calendar.MONDAY,
//    Calendar.TUESDAY,
//    Calendar.WEDNESDAY,
//    Calendar.THURSDAY,
//    Calendar.FRIDAY,
//    Calendar.SATURDAY
//)
//
///** Quick-pick offsets shown under the minutes field. */
//private val MINUTE_PRESETS = listOf(5, 10, 15, 20, 30, 45, 60)
//
//private const val MAX_OFFSET_MINUTES = 180
//private const val MAX_OFFSET_DIGITS = 3
//
///**
// * Bottom sheet for creating or editing a custom alarm.
// *
// * @param existingAlarms every saved alarm, used to warn live when the draft would fire at the same
// * time as another alarm (the alarm being edited is ignored).
// */
//@OptIn(ExperimentalMaterial3Api::class)
////Original
//@Composable
//fun AddEditAlarmSheet(
//    existingAlarm: CustomAlarmUi?,
//    existingAlarms: List<CustomAlarmUi>,
//    onSave: (CustomAlarmUi) -> Unit,
//    onDismiss: () -> Unit
//) {
//    var prayerName by remember(existingAlarm) {
//        mutableStateOf(existingAlarm?.prayerName ?: PrayerNameUi.entries.first())
//    }
//    var label by remember(existingAlarm) {
//        mutableStateOf(existingAlarm?.label ?: "")
//    }
//    // Held as text so the field can be cleared while typing; parsed on demand.
//    var minutesText by remember(existingAlarm) {
//        mutableStateOf((existingAlarm?.offsetMinutes ?: 5).toString())
//    }
//    var offsetDirection by remember(existingAlarm) {
//        mutableStateOf(existingAlarm?.offsetDirection ?: OffsetDirectionUi.BEFORE)
//    }
//    var everyDay by remember(existingAlarm) {
//        mutableStateOf(existingAlarm?.everyDay ?: true)
//    }
//    var activeDays by remember(existingAlarm) {
//        mutableStateOf(existingAlarm?.activeDays ?: emptySet())
//    }
//    var isPrayerMenuExpanded by remember { mutableStateOf(false) }
//
//    val offsetMinutes = minutesText.toIntOrNull()
//    val hasDays = everyDay || activeDays.isNotEmpty()
//    val draft = offsetMinutes?.let {
//        CustomAlarmUi(
//            id = existingAlarm?.id ?: 0L,
//            prayerName = prayerName,
//            label = label.trim(),
//            offsetMinutes = it,
//            offsetDirection = offsetDirection,
//            everyDay = everyDay,
//            activeDays = if (everyDay) emptySet() else activeDays,
//            isEnabled = existingAlarm?.isEnabled ?: true
//        )
//    }
//    val hasConflict = draft != null && existingAlarms.any {
//        it.id != draft.id && it.conflictsWith(draft)
//    }
//    val canSave = draft != null && hasDays && !hasConflict
//
//    val locale = LocalConfiguration.current.locales[0]
//    val numberFormat = remember(locale) { NumberFormat.getInstance(locale) }
//    val minutesUnit = stringResource(R.string.minutes_short)
//
//    // Opens fully instead of stopping at half height.
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        sheetState = sheetState,
//        containerColor = MaterialTheme.colorScheme.background
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .imePadding()
//                .verticalScroll(rememberScrollState())
//                .padding(horizontal = 20.dp)
//                .padding(bottom = 24.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                text = stringResource(
//                    if (existingAlarm == null) R.string.new_custom_alarm else R.string.edit_custom_alarm
//                ),
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//
//            OutlinedTextField(
//                value = label,
//                onValueChange = { label = it },
//                label = { Text(stringResource(R.string.alarm_label_optional)) },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth(),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = MaterialTheme.colorScheme.primary,
//                    focusedLabelColor = MaterialTheme.colorScheme.primary
//                )
//            )
//
//            ExposedDropdownMenuBox(
//                expanded = isPrayerMenuExpanded,
//                onExpandedChange = { isPrayerMenuExpanded = it }
//            ) {
//                OutlinedTextField(
//                    value = stringResource(prayerName.labelRes),
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text(stringResource(R.string.prayer)) },
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPrayerMenuExpanded) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = MaterialTheme.colorScheme.primary,
//                        focusedLabelColor = MaterialTheme.colorScheme.primary
//                    )
//                )
//                ExposedDropdownMenu(
//                    expanded = isPrayerMenuExpanded,
//                    onDismissRequest = { isPrayerMenuExpanded = false }
//                ) {
//                    PrayerNameUi.entries.forEach { name ->
//                        DropdownMenuItem(
//                            text = { Text(stringResource(name.labelRes)) },
//                            onClick = { prayerName = name; isPrayerMenuExpanded = false }
//                        )
//                    }
//                }
//            }
//
//            Column {
//                Text(
//                    stringResource(R.string.timing),
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
//                    SegmentedButton(
//                        selected = offsetDirection == OffsetDirectionUi.BEFORE,
//                        onClick = { offsetDirection = OffsetDirectionUi.BEFORE },
//                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
//                        colors = SegmentedButtonDefaults.colors(
//                            activeContainerColor = MaterialTheme.colorScheme.primary,
//                            activeContentColor = MaterialTheme.colorScheme.onPrimary
//                        )
//                    ) { Text(stringResource(R.string.before)) }
//                    SegmentedButton(
//                        selected = offsetDirection == OffsetDirectionUi.AFTER,
//                        onClick = { offsetDirection = OffsetDirectionUi.AFTER },
//                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
//                        colors = SegmentedButtonDefaults.colors(
//                            activeContainerColor = MaterialTheme.colorScheme.primary,
//                            activeContentColor = MaterialTheme.colorScheme.onPrimary
//                        )
//                    ) { Text(stringResource(R.string.after)) }
//                }
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                // Typed minutes (0..180). Digits only; larger numbers snap to the maximum.
//                OutlinedTextField(
//                    value = minutesText,
//                    onValueChange = { input ->
//                        val digits = input.filter { it.isDigit() }.take(MAX_OFFSET_DIGITS)
//                        val value = digits.toIntOrNull()
//                        minutesText = if (value != null && value > MAX_OFFSET_MINUTES) {
//                            MAX_OFFSET_MINUTES.toString()
//                        } else {
//                            digits
//                        }
//                    },
//                    label = { Text(stringResource(R.string.alarm_minutes_field_label)) },
//                    suffix = { Text(minutesUnit) },
//                    singleLine = true,
//                    isError = hasConflict,
//                    supportingText = {
//                        // Inline, inside the sheet: a snackbar would be hidden behind the scrim.
//                        if (hasConflict) Text(stringResource(R.string.duplicate_alarm_exists))
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Number,
//                        imeAction = ImeAction.Done
//                    ),
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = MaterialTheme.colorScheme.primary,
//                        focusedLabelColor = MaterialTheme.colorScheme.primary
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                    items(MINUTE_PRESETS) { preset ->
//                        FilterChip(
//                            selected = offsetMinutes == preset,
//                            onClick = { minutesText = preset.toString() },
//                            label = { Text("${numberFormat.format(preset)} $minutesUnit") },
//                            colors = FilterChipDefaults.filterChipColors(
//                                selectedContainerColor = MaterialTheme.colorScheme.primary,
//                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
//                            )
//                        )
//                    }
//                }
//            }
//
//            Column {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        stringResource(R.string.repeat_every_day),
//                        style = MaterialTheme.typography.labelLarge,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Switch(
//                        checked = everyDay,
//                        onCheckedChange = { everyDay = it },
//                        colors = SwitchDefaults.colors(
//                            checkedThumbColor = MaterialTheme.colorScheme.primary,
//                            checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
//                        )
//                    )
//                }
//
//                if (!everyDay) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                        items(WEEK_DAYS) { day ->
//                            val selected = activeDays.contains(day)
//                            FilterChip(
//                                selected = selected,
//                                onClick = {
//                                    activeDays = if (selected) activeDays - day else activeDays + day
//                                },
//                                label = { Text(dayShortLabel(day, locale)) },
//                                colors = FilterChipDefaults.filterChipColors(
//                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
//                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//
//            Button(
//                onClick = { draft?.let(onSave) },
//                enabled = canSave,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary
//                ),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(stringResource(R.string.save))
//            }
//        }
//    }
//}
//
///** Localized short weekday name for a [Calendar] day constant (Sunday = 1 ... Saturday = 7). */
//private fun dayShortLabel(calendarDay: Int, locale: Locale): String =
//    DayOfWeek.of((calendarDay + 5) % 7 + 1).getDisplayName(DayNameStyle.SHORT, locale)