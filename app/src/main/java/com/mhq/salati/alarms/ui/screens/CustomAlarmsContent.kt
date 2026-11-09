package com.mhq.salati.alarms.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.alarms.ui.components.AddEditAlarmFormContent
import com.mhq.salati.alarms.ui.components.AddEditAlarmSheet
import com.mhq.salati.alarms.ui.components.CustomAlarmCard
import com.mhq.salati.shared.ui.components.TabHeader
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults

@Composable
fun CustomAlarmsContent(
    state: AlarmsContract.State,
    onIntent: (AlarmsContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    var headerHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val showSidePanel = isLandscape && state.isEditorVisible

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (isLandscape) {
            // Layout splits everything to the left of the Floating Action Button zone
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 88.dp) // Leave clean space for the FAB container on the right edge
                    .padding(top = with(density) { headerHeightPx.toDp() } + 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Left Column: 50% screen real estate list of Alarms
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
                            contentPadding = PaddingValues(start = 16.dp, end = 8.dp, bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.alarms, key = { it.id }) { alarm ->
                                CustomAlarmCard(
                                    customAlarm = alarm,
                                    onToggle = { enabled -> onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)) },
                                    onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
                                    onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) },
                                    onDayToggle = { day -> onIntent(AlarmsContract.Intent.AlarmDayToggled(alarm.id, day)) }
                                )
                            }
                        }
                    }
                }

                // Right Column: 50% screen split for the layout form wireframe
                if (showSidePanel) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(end = 8.dp, start = 8.dp)
                    ) {
                        AddEditAlarmFormContent(
                            existingAlarm = state.editingAlarm,
                            existingAlarms = state.alarms,
                            onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) },
                            onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) }
                        )
                    }
                } else {
                    // Blank space holder matching equal weight constraint parameters when form is hidden
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        } else {
            // Portrait view footprint restored
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
                        start = 16.dp,
                        end = 16.dp,
                        top = with(density) { headerHeightPx.toDp() } + 16.dp,
                        bottom = BottomNavBarDefaults.Height + 80.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.alarms, key = { it.id }) { alarm ->
                        CustomAlarmCard(
                            customAlarm = alarm,
                            onToggle = { enabled -> onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)) },
                            onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
                            onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) },
                            onDayToggle = { day -> onIntent(AlarmsContract.Intent.AlarmDayToggled(alarm.id, day)) }
                        )
                    }
                }
            }
        }

        // TabHeader strictly placed at Top across layout contexts
        TabHeader(
            icon = Icons.Default.Alarm,
            title = stringResource(R.string.custom_alarms),
            subtitle = stringResource(R.string.create_custom_alarms_before_or_after_prayers),
            modifier = Modifier.onGloballyPositioned {
                headerHeightPx = it.size.height
            }
        )

        // Floating Action Button perfectly pinned to the absolute bottom-end area across screens
        FloatingActionButton(
            onClick = { onIntent(AlarmsContract.Intent.AddAlarm) },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(
                    bottom = BottomNavBarDefaults.Height + 20.dp,
                    end = 20.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_custom_alarm)
            )
        }

        if (!isLandscape && state.isEditorVisible) {
            AddEditAlarmSheet(
                existingAlarm = state.editingAlarm,
                existingAlarms = state.alarms,
                onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) },
                onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) }
            )
        }
    }
}




//package com.mhq.salati.alarms.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Alarm
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.mhq.salati.R
//import com.mhq.salati.alarms.presentation.contract.AlarmsContract
//import com.mhq.salati.alarms.ui.components.AddEditAlarmSheet
//import com.mhq.salati.alarms.ui.components.CustomAlarmCard
//import com.mhq.salati.shared.ui.components.TabHeader
//import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
//
////Original
//@Composable
//fun CustomAlarmsContent(
//    state: AlarmsContract.State,
//    onIntent: (AlarmsContract.Intent) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var headerHeightPx by remember { mutableIntStateOf(0) }
//    val density = LocalDensity.current
//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        when {
//            state.isLoading -> CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center),
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            state.alarms.isEmpty() -> CustomAlarmsContentBlank(
//                modifier = Modifier.align(Alignment.Center)
//            )
//
//            else -> LazyColumn(
//                contentPadding = PaddingValues(
//                    start = 16.dp,
//                    end = 16.dp,
//                    top = with(density) { headerHeightPx.toDp() } + 16.dp,
//                    bottom = 48.dp
//                ),
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(state.alarms, key = { it.id }) { alarm ->
//                    CustomAlarmCard(
//                        customAlarm = alarm,
//                        onToggle = { enabled ->
//                            onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled))
//                        },
//                        onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
//                        onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) },
//                        onDayToggle = { day ->
//                            onIntent(AlarmsContract.Intent.AlarmDayToggled(alarm.id, day))
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(12.dp))
//                }
//                item {
//                    Spacer(modifier = Modifier.height(BottomNavBarDefaults.Height + 16.dp))
//                }
//            }
//        }
//
//        TabHeader(
//            icon = Icons.Default.Alarm,
//            title = stringResource(R.string.custom_alarms),
//            subtitle = stringResource(R.string.create_custom_alarms_before_or_after_prayers),
//            modifier = Modifier.onGloballyPositioned {
//                headerHeightPx = it.size.height
//            }
//        )
//
//        FloatingActionButton(
//            onClick = { onIntent(AlarmsContract.Intent.AddAlarm) },
//            containerColor = MaterialTheme.colorScheme.tertiary,
//            contentColor = MaterialTheme.colorScheme.onTertiary,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .navigationBarsPadding()
//                .padding(bottom = BottomNavBarDefaults.Height + 20.dp, end = 20.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = stringResource(R.string.add_custom_alarm)
//            )
//        }
//
//        if (state.isEditorVisible) {
//            AddEditAlarmSheet(
//                existingAlarm = state.editingAlarm,
//                existingAlarms = state.alarms,
//                onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) },
//                onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) }
//            )
//        }
//    }
//}