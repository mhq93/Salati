package com.mhq.salati.alarms.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.ui.components.AddEditAlarmSheet
import com.mhq.salati.alarms.ui.components.CustomAlarmCard
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.components.TabHeader

@Composable
fun CustomAlarmsContent(
    state: AlarmsContract.State,
    onIntent: (AlarmsContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    var headerHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                    start = 16.dp,
                    end = 16.dp,
                    top = with(density) { headerHeightPx.toDp() } + 16.dp,
                    bottom = 48.dp
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.alarms, key = { it.id }) { alarm ->
                    CustomAlarmCard(
                        customAlarm = alarm,
                        onToggle = { enabled ->
                            onIntent(AlarmsContract.Intent.ToggleAlarm(alarm.id, enabled))
                        },
                        onEdit = { onIntent(AlarmsContract.Intent.EditAlarm(alarm)) },
                        onDelete = { onIntent(AlarmsContract.Intent.DeleteAlarm(alarm.id)) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(BottomNavBarDefaults.Height + 16.dp))
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
            onClick = { onIntent(AlarmsContract.Intent.AddAlarm) },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = BottomNavBarDefaults.Height + 20.dp, end = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_custom_alarm)
            )
        }

        if (state.isEditorVisible) {
            AddEditAlarmSheet(
                existingAlarm = state.editingAlarm,
                onDismiss = { onIntent(AlarmsContract.Intent.DismissEditor) },
                onSave = { alarm -> onIntent(AlarmsContract.Intent.SaveAlarm(alarm)) }
            )
        }
    }
}