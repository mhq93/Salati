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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.presentation.theme.Dolphin
import com.mhq.salati.presentation.theme.Obsidian
import com.mhq.salati.presentation.theme.SalatiTheme
import com.mhq.salati.presentation.theme.Timberwolf

@Composable
fun SettingsActionRow(
    icon: ImageVector,
    title: String,
    trailingText: String? = null,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .let {
                if (onClick != null)
                    it.clickable(onClick = onClick)
                else
                    it
            }
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
    ) {
        SettingsIconBadge(icon)

        Spacer(
            modifier.width(14.dp)
        )

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Obsidian,
            modifier = modifier.weight(1f)
        )

        trailingText?.let {
            Text(
                text = it,
                fontSize = 13.sp,
                color = Dolphin
            )
        } ?: run {
            if (onClick != null) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    tint = Timberwolf,
                    contentDescription = null,
                    modifier = modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsActionRowPreview() {
    SalatiTheme() {
        SettingsActionRow(
            icon = Icons.Filled.CheckCircle,
            title = "title",
            trailingText = "trailingText",
            onClick = {}
        )
    }
}