package com.mhq.salati.settings.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.theme.SalatiTheme

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
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        SettingsIconBadge(icon)
        Spacer(Modifier.width(14.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onDecrement, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.RemoveCircleOutline,
                contentDescription = "Decrease",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = if (value > 0) "+$value" else "$value",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 6.dp)
        )
        IconButton(onClick = onIncrement, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.AddCircleOutline,
                contentDescription = "Increase",
                tint = MaterialTheme.colorScheme.primary
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