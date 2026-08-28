// SettingsIconBadge.kt
package com.mhq.salati.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsIconBadge(
    icon: ImageVector,
    isEnabled: Boolean = true
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = if (isEnabled) 0.1f else 0.05f)), // <-- Replaced DarkGreen
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.35f), // <-- Replaced DarkGreen
            modifier = Modifier.size(20.dp)
        )
    }
}

//package com.mhq.salati.settings.presentation.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material3.Icon
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.mhq.salati.shared.presentation.theme.DarkGreen
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//
//@Composable
//fun SettingsIconBadge(
//    icon: ImageVector,
//    isEnabled: Boolean = true
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .size(36.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .background(DarkGreen.copy(alpha = if (isEnabled) 0.1f else 0.05f)),
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = if (isEnabled) DarkGreen else DarkGreen.copy(alpha = 0.35f),
//            modifier = Modifier.size(20.dp)
//        )
//    }
//}
//
//@Preview
//@Composable
//fun SettingsIconBadgePreview() {
//    SalatiTheme() {
//        SettingsIconBadge(
//            icon = Icons.Filled.CheckCircle,
//            isEnabled = true
//        )
//    }
//}