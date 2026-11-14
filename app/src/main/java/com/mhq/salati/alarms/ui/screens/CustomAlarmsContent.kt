package com.mhq.salati.alarms.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.alarms.ui.components.AddEditAlarmFormContent
import com.mhq.salati.alarms.ui.components.AddEditAlarmSheet
import com.mhq.salati.alarms.ui.components.CustomAlarmCard
import com.mhq.salati.alarms.ui.state.AlarmFormState
import com.mhq.salati.shared.ui.ScreenHorizontalPadding
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.screenHorizontalMargin
import com.mhq.salati.shared.ui.symmetricCutoutInset

private val FabColumnWidth = 56.dp + ScreenHorizontalPadding + 12.dp

@Composable
fun CustomAlarmsContent(
    state: AlarmsContract.State,
    onIntent: (AlarmsContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    var headerHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val isLandscape = isWideLayout()
    val showSidePanel = isLandscape && state.isEditorVisible

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val formState = remember(state.isEditorVisible, state.editingAlarm) {
            AlarmFormState(state.editingAlarm)
        }

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.navigationBars.only(
                            WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                        )
                    )
                    .padding(
                        start = screenHorizontalMargin(),
                        end = FabColumnWidth + symmetricCutoutInset(),
                        top = with(density) { headerHeightPx.toDp() } + 12.dp,
                        bottom = BottomNavBarDefaults.Height
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else if (state.alarms.isEmpty()) {
                        CustomAlarmsContentBlank(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.alarms, key = { it.id }) { alarm ->
                                CustomAlarmCard(
                                    customAlarm = alarm,
                                    areNotificationsOff = !state.notificationsEnabled,
                                    onToggle = { enabled -> onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)) },
                                    onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
                                    onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) },
                                    onDayToggle = { day -> onIntent(AlarmsContract.Intent.AlarmDayToggled(alarm.id, day)) }
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    if (showSidePanel) {
                        AddEditAlarmFormContent(
                            formState = formState,
                            existingAlarm = state.editingAlarm,
                            existingAlarms = state.alarms,
                            onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) },
                            onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) }
                        )
                    }
                }
            }
        } else {
            /** Portrait view footprint restored */
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (state.alarms.isEmpty()) {
                CustomAlarmsContentBlank(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = ScreenHorizontalPadding,
                        end = ScreenHorizontalPadding,
                        top = with(density) { headerHeightPx.toDp() } + 16.dp,
                        bottom = BottomNavBarDefaults.Height + 80.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.alarms, key = { it.id }) { alarm ->
                        CustomAlarmCard(
                            customAlarm = alarm,
                            areNotificationsOff = !state.notificationsEnabled,
                            onToggle = { enabled -> onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)) },
                            onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
                            onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) },
                            onDayToggle = { day -> onIntent(AlarmsContract.Intent.AlarmDayToggled(alarm.id, day)) }
                        )
                    }
                }
            }
        }

        /** TabHeader strictly placed at Top across layout contexts */
        TabHeader(
            icon = Icons.Default.Alarm,
            title = stringResource(R.string.custom_alarms),
            subtitle = stringResource(R.string.create_custom_alarms_before_or_after_prayers),
            modifier = Modifier.onGloballyPositioned {
                headerHeightPx = it.size.height
            }
        )

        /** Floating Action Button pinned to the absolute bottom-end area across screens */
        FloatingActionButton(
            onClick = { onIntent(AlarmsContract.Intent.AddAlarm) },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(
                    bottom = BottomNavBarDefaults.Height + 20.dp,
                    end = screenHorizontalMargin()
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_custom_alarm)
            )
        }

        if (!isLandscape && state.isEditorVisible) {
            AddEditAlarmSheet(
                formState = formState,
                existingAlarm = state.editingAlarm,
                existingAlarms = state.alarms,
                onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) },
                onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) }
            )
        }
    }
}