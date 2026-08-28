package com.mhq.salati.qibla.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R

@Composable
fun LocationPill(
    locationName: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .border(
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) // <-- Replaced DarkGreen
                ),
                shape = CircleShape
            )
            .background(MaterialTheme.colorScheme.surface) // <-- Replaced SheetBackground
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .background(MaterialTheme.colorScheme.secondary, CircleShape), // <-- Replaced AccentGreen
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                tint = MaterialTheme.colorScheme.onSecondary, // <-- Replaced Color.White
                contentDescription = "Prayer Location",
                modifier = Modifier.size(14.dp)
            )
        }
        Text(
            text = locationName ?: stringResource(R.string.location_not_found),
            color = MaterialTheme.colorScheme.onSurface, // <-- Replaced InkText
            fontSize = 16.dp.value.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .background(MaterialTheme.colorScheme.secondary, CircleShape), // <-- Replaced AccentGreen
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = MaterialTheme.colorScheme.onSecondary, // <-- Replaced Color.White
                contentDescription = "Location Drop-down",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

//package com.mhq.salati.qibla.presentation.components
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.RectangleShape
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.R
//import com.mhq.salati.shared.presentation.theme.AccentGreen
//import com.mhq.salati.shared.presentation.theme.DarkGreen
//import com.mhq.salati.shared.presentation.theme.InkText
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//import com.mhq.salati.shared.presentation.theme.SheetBackground
//
//@Composable
//fun LocationPill(
//    locationName: String?,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = modifier
//            .clip(RoundedCornerShape(50))
//            .border(
//                border = BorderStroke(
//                    1.dp,
//                    DarkGreen.copy(alpha = 0.08f)
//                ),
//                shape = CircleShape
//            )
//            .background(SheetBackground)
//            .clickable(onClick = onClick)
//            .padding(
//                horizontal = 16.dp,
//                vertical = 8.dp
//            )
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .size(24.dp)
//                .background(AccentGreen, CircleShape),
//        ) {
//            Icon(
//                imageVector = Icons.Default.LocationOn,
//                tint = Color.White,
//                contentDescription = "Prayer Location",
//                modifier = Modifier.size(14.dp)
//            )
//        }
//        Text(
//            text = locationName ?: stringResource(R.string.location_not_found),
//            color = InkText,
//            fontSize = 16.dp.value.sp,
//            fontWeight = FontWeight.Medium,
//            maxLines = 1,
//            modifier = Modifier.padding(horizontal = 12.dp)
//        )
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .size(24.dp)
//                .background(AccentGreen, CircleShape),
//        ) {
//            Icon(
//                imageVector = Icons.Default.KeyboardArrowDown,
//                tint = Color.White,
//                contentDescription = "Location Drop-down",
//                modifier = Modifier.size(16.dp)
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun LocationPillPreview() {
//    SalatiTheme() {
//        LocationPill(
//            locationName = "Alexandria, Egypt",
//            onClick = {}
//        )
//    }
//}