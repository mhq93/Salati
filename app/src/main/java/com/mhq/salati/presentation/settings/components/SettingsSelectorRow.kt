package com.mhq.salati.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.presentation.theme.Obsidian
import com.mhq.salati.presentation.theme.QuickSilver
import com.mhq.salati.presentation.theme.SalatiTheme
import com.mhq.salati.presentation.theme.Timberwolf

@Composable
fun SettingsSelectorRow(
    icon: ImageVector,
    title: String,
    valueLabel: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .then(if (isEnabled) Modifier else Modifier)
            .clickable(enabled = isEnabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        SettingsIconBadge(icon, isEnabled)

        Spacer(modifier.width(14.dp))

        Text(
            title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = if (isEnabled) Obsidian else QuickSilver,
            modifier = modifier.weight(1f)
        )

        Text(
            valueLabel,
            fontSize = 13.sp,
            color = if (isEnabled) Color(0xFF8A8A8A) else QuickSilver
        )

        Spacer(Modifier.width(4.dp))

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Timberwolf,
            modifier = modifier.size(18.dp)
        )
    }
}

@Preview
@Composable
private fun SettingsSelectorRowPreview() {
    SalatiTheme() {
        SettingsSelectorRow(
            icon = Icons.Filled.CheckCircle,
            title = "title",
            valueLabel = "valueLabel",
            isEnabled = true,
            onClick = {}
        )
    }
}