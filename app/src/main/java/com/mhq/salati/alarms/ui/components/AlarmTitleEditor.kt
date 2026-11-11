package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import com.mhq.salati.R

private const val MAX_LABEL_LENGTH = 30

@Composable
internal fun AlarmTitleEditor(
    label: String,
    onLabelChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultTitle = stringResource(R.string.custom_alarm_default_title)
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isEditing) {
        if (isEditing) focusRequester.requestFocus()
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = label,
                onValueChange = { onLabelChange(it.take(MAX_LABEL_LENGTH)) },
                placeholder = { Text(defaultTitle) },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { isEditing = false }),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
            )
        } else {
            Text(
                text = label.trim().ifBlank { defaultTitle },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f, fill = false)
                    .pointerInput(Unit) {
                        detectTapGestures(onDoubleTap = { isEditing = true })
                    }
            )
        }
        IconButton(onClick = { isEditing = !isEditing }) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = stringResource(if (isEditing) R.string.save else R.string.edit),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}