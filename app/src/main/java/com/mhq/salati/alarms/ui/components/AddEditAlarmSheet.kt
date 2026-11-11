package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.ui.state.AlarmFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAlarmSheet(
    formState: AlarmFormState,
    existingAlarm: CustomAlarmUi?,
    existingAlarms: List<CustomAlarmUi>,
    onSave: (CustomAlarmUi) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
                formState = formState,
                existingAlarm = existingAlarm,
                existingAlarms = existingAlarms,
                onSave = onSave,
                onDismiss = onDismiss
            )
        }
    }
}