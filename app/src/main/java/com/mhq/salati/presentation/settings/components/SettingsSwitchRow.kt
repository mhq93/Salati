package com.mhq.salati.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.mhq.salati.presentation.theme.DarkGreen
import com.mhq.salati.presentation.theme.Dolphin
import com.mhq.salati.presentation.theme.Obsidian
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
    ) {
        SettingsIconBadge(icon)

        Spacer(modifier.width(14.dp))

        Column(
            modifier.weight(1f)
        ) {
            Text(
                title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Obsidian
            )
            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = Dolphin
                )
            }
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = DarkGreen
            )
        )
    }
}

@Preview
@Composable
private fun SettingsSwitchRowPreview() {
    SalatiTheme() {
        SettingsSwitchRow(
            icon = Icons.Filled.CheckCircle,
            title = "title",
            subtitle = "subtitle",
            isChecked = true,
            onCheckedChange = {}
        )
    }
}