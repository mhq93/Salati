package com.mhq.salati.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun SettingsStepperRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
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
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Obsidian
            )

            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Dolphin
            )
        }
        IconButton(
            onClick = onDecrement,
            modifier = modifier.size(32.dp)
        ) {
            Icon(
                Icons.Default.RemoveCircleOutline,
                contentDescription = "Decrease",
                tint = DarkGreen
            )
        }

        Text(
            text = if (value > 0) "+$value" else "$value",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkGreen,
            modifier = modifier.padding(horizontal = 6.dp)
        )
        IconButton(
            onClick = onIncrement,
            modifier = modifier.size(32.dp)
        ) {
            Icon(
                Icons.Default.AddCircleOutline,
                contentDescription = "Increase",
                tint = DarkGreen
            )
        }
    }
}

@Preview
@Composable
private fun SettingsStepperRowPreview() {
    SalatiTheme() {
        SettingsStepperRow(
            icon = Icons.Filled.CheckCircle,
            title = "title",
            subtitle = "subtitle",
            value = 1,
            onIncrement = {},
            onDecrement = {}
        )
    }
}