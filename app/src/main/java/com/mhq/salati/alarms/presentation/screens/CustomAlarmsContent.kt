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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.alarms.presentation.components.AddEditAlarmSheet
import com.mhq.salati.alarms.presentation.components.CustomAlarmCard
import com.mhq.salati.alarms.presentation.contract.AlarmsContract
import com.mhq.salati.shared.presentation.components.BottomNavDefaults

@Composable
fun CustomAlarmsContent(
    state: AlarmsContract.State,
    onIntent: (AlarmsContract.Intent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
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
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 48.dp)
            ) {
                items(state.alarms, key = { it.id }) { alarm ->
                    CustomAlarmCard(
                        alarm = alarm,
                        onToggle = { enabled ->
                            onIntent(
                                AlarmsContract.Intent.ToggleAlarm(
                                    alarm.id,
                                    enabled
                                )
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

        FloatingActionButton(
            onClick = { onIntent(AlarmsContract.Intent.AddAlarmClicked) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
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