package com.mhq.salati.language.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mhq.salati.settings.presentation.model.AppLanguageUi

@Composable
fun LanguageSelectionDialog(
    onLanguageSelected: (AppLanguageUi) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        LanguageSelectionContent(
            onLanguageSelected = onLanguageSelected,
            modifier = modifier
        )
    }
}