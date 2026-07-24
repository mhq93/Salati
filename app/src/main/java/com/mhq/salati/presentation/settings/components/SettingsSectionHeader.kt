package com.mhq.salati.presentation.settings.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.presentation.theme.DarkGreen
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun SettingsSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.sp,
        color = DarkGreen.copy(alpha = 0.6f),
        modifier = modifier.padding(
            bottom = 8.dp,
            start = 4.dp
        )
    )
}

@Preview
@Composable
private fun SettingsSectionHeaderPreview() {
    SalatiTheme() {
        SettingsSectionHeader("SettingsSectionHeader")
    }
}