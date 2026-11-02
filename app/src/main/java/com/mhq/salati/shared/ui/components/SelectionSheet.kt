package com.mhq.salati.shared.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R

/** One row of a [SelectionSheet]. */
data class SelectionOption(
    @StringRes val labelRes: Int,
    val isSelected: Boolean,
    val onSelect: () -> Unit
)

/** Builds one option per entry; the entry equal to [selected] is marked. */
fun <T> selectionOptions(
    entries: List<T>,
    selected: T,
    label: (T) -> Int,
    onSelect: (T) -> Unit
): List<SelectionOption> = entries.map { entry ->
    SelectionOption(
        labelRes = label(entry),
        isSelected = entry == selected,
        onSelect = { onSelect(entry) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionSheet(
    @StringRes titleRes: Int,
    options: List<SelectionOption>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier.padding(bottom = 24.dp)) {
            Text(
                text = stringResource(titleRes),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

            LazyColumn {
                items(options) { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { option.onSelect() }
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = stringResource(option.labelRes),
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )
                        if (option.isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.is_selected)
                            )
                        }
                    }
                }
            }
        }
    }
}