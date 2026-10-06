package com.mhq.salati.settings.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun SettingsDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        modifier = modifier.padding(start = 60.dp)
    )
}

@Preview
@Composable
private fun SettingsDividerPreview() {
    SalatiTheme() {
        SettingsDivider()
    }
}