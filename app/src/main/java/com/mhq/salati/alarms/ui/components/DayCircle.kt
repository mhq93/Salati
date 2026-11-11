package com.mhq.salati.alarms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
internal fun DayCircle(
    badge: DayBadge,
    isActive: Boolean,
    isAlarmEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val backgroundColor = when {
        !isActive -> colors.onSurface.copy(alpha = 0.05f)
        isAlarmEnabled -> colors.primary.copy(alpha = 0.12f)
        else -> colors.onSurfaceVariant.copy(alpha = 0.1f)
    }
    val textColor = when {
        !isActive -> colors.onSurfaceVariant.copy(alpha = 0.35f)
        isAlarmEnabled -> colors.primary
        else -> colors.onSurfaceVariant.copy(alpha = 0.7f)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .toggleable(value = isActive, role = Role.Checkbox, onValueChange = { onClick() })
            .semantics { contentDescription = badge.fullName }
    ) {
        Text(
            text = badge.initial,
            fontSize = 13.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            modifier = Modifier.clearAndSetSemantics { }
        )
    }
}