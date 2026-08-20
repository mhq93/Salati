package com.mhq.salati.alarms.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.components.AddEditAlarmSheet
import com.mhq.salati.alarms.presentation.components.CustomAlarmCard
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.shared.presentation.components.BottomNavDefaults
import com.mhq.salati.shared.presentation.components.TabHeader
import com.mhq.salati.shared.presentation.theme.AccentOrange
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.SheetBackground

//@Composable
//fun CustomAlarmsContent(
//    state: AlarmsContract.State,
//    onIntent: (AlarmsContract.Intent) -> Unit
//) {
//    val density = LocalDensity.current
//    var headerHeightPx by remember { mutableIntStateOf(0) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                MaterialTheme.colorScheme.background
//            )
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
//                    horizontal = 16.dp,
//                    vertical = 48.dp
//                ).let {
//                    PaddingValues(
//                        start = 16.dp,
//                        end = 16.dp,
//                        top = with(density) { headerHeightPx.toDp() } + 16.dp,
//                        bottom = 48.dp
//                    )
//                },
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(state.alarms, key = { it.id }) { alarm ->
//                    CustomAlarmCard(
//                        customAlarm = alarm,
//                        onToggle = { enabled ->
//                            onIntent(
//                                AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)
//                            )
//                        },
//                        onEdit = { onIntent(AlarmsContract.Intent.EditAlarmClicked(alarm)) },
//                        onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) }
//                    )
//                    Spacer(
//                        modifier = Modifier.height(12.dp)
//                    )
//                }
//                item {
//                    Spacer(
//                        modifier = Modifier.height(BottomNavDefaults.Height + 16.dp)
//                    )
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
//            onClick = { onIntent(AlarmsContract.Intent.AddAlarmClicked) },
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.onPrimary,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .navigationBarsPadding()
//                .padding(bottom = BottomNavDefaults.Height + 20.dp, end = 20.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = stringResource(R.string.add_custom_alarm)
//            )
//        }
//    }
//
//    if (state.isEditorVisible) {
//        AddEditAlarmSheet(
//            existingAlarm = state.editingAlarm,
//            onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) },
//            onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) }
//        )
//    }
//}

@Composable
fun CustomAlarmsContent(
    state: AlarmsContract.State,
    onIntent: (AlarmsContract.Intent) -> Unit
) {
    val density = LocalDensity.current
    var headerHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SheetBackground)
    ) {
        when {
            state.isLoading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )

            state.alarms.isEmpty() -> CustomAlarmsContentBlank(
                modifier = Modifier.align(Alignment.Center)
            )

            else -> LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 48.dp
                ).let {
                    PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = with(density) { headerHeightPx.toDp() } + 16.dp,
                        bottom = 48.dp
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.alarms, key = { it.id }) { alarm ->
                    CustomAlarmCard(
                        customAlarm = alarm,
                        onToggle = { enabled ->
                            onIntent(
                                AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled)
                            )
                        },
                        onEdit = { onIntent(AlarmsContract.Intent.EditAlarmClicked(alarm)) },
                        onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) }
                    )
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
                item {
                    Spacer(
                        modifier = Modifier.height(BottomNavDefaults.Height + 16.dp)
                    )
                }
            }
        }

        TabHeader(
            icon = Icons.Default.Alarm,
            title = stringResource(R.string.custom_alarms),
            subtitle = stringResource(R.string.create_custom_alarms_before_or_after_prayers),
            modifier = Modifier.onGloballyPositioned {
                headerHeightPx = it.size.height
            }
        )

        FloatingActionButton(
            onClick = { onIntent(AlarmsContract.Intent.AddAlarmClicked) },
            containerColor = AccentOrange,
            contentColor = DarkGreen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = BottomNavDefaults.Height + 20.dp, end = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_custom_alarm)
            )
        }
    }

    if (state.isEditorVisible) {
        AddEditAlarmSheet(
            existingAlarm = state.editingAlarm,
            onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) },
            onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) }
        )
    }
}