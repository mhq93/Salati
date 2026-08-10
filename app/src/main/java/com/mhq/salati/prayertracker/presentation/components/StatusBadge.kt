package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun StatusBadge(
    icon: ImageVector,
    color: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.15f)),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Status Badge",
            tint = color,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
private fun StatusBadgePreview() {
    SalatiTheme() {
        StatusBadge(
            icon = Icons.Filled.CheckCircle,
            color = Color.Yellow
        )
    }
}