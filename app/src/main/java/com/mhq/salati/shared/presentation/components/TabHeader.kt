package com.mhq.salati.shared.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.prayerGradient

@Composable
fun TabHeader(
    icon: ImageVector,
    title: String,
    subtitle: String,
    trailingContent: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.prayerGradient) // <-- Replaced manual verticalGradient
            .statusBarsPadding()
            .padding(top = 24.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f)), // <-- Replaced Color.White.copy
                ) {
                    Icon(
                        imageVector = icon,
                        tint = MaterialTheme.colorScheme.tertiary, // <-- Replaced AccentGold
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onPrimary, // <-- Replaced Color.White
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f), // <-- Replaced Color.White.copy
                        fontSize = 14.sp
                    )
                }
            }
            trailingContent()
        }
    }
}

@Preview
@Composable
private fun TabHeaderPreview() {
    SalatiTheme {
        TabHeader(
            icon = Icons.Default.Explore,
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}

//package com.mhq.salati.shared.presentation.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Explore
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.shared.presentation.theme.AccentGold
//import com.mhq.salati.shared.presentation.theme.DarkGreen
//import com.mhq.salati.shared.presentation.theme.DarkGreenLight
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//
//@Composable
//fun TabHeader(
//    icon: ImageVector,
//    title: String,
//    subtitle: String,
//    trailingContent: @Composable () -> Unit = {},
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(
//                Brush.verticalGradient(
//                    listOf(DarkGreenLight, DarkGreen)
//                )
//            )
//            .statusBarsPadding()
//            .padding(
//                top = 24.dp,
//                bottom = 32.dp,
//                start = 24.dp,
//                end = 24.dp
//            )
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.Top,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.weight(1f, fill = false)
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .size(56.dp)
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(Color.White.copy(alpha = 0.12f)),
//                ) {
//                    Icon(
//                        imageVector = icon,
//                        tint = AccentGold,
//                        contentDescription = null,
//                        modifier = Modifier.size(28.dp)
//                    )
//                }
//                Column(
//                    modifier = Modifier.padding(start = 16.dp)
//                ) {
//                    Text(
//                        text = title,
//                        color = Color.White,
//                        fontSize = 26.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(
//                        Modifier.height(4.dp)
//                    )
//                    Text(
//                        text = subtitle,
//                        color = Color.White.copy(alpha = 0.7f),
//                        fontSize = 14.sp
//                    )
//                }
//            }
//            trailingContent()
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun TabHeaderPreview() {
//    SalatiTheme() {
//        TabHeader(
//            icon = Icons.Default.Explore,
//            title = "Title",
//            subtitle = "Subtitle"
//        )
//    }
//}