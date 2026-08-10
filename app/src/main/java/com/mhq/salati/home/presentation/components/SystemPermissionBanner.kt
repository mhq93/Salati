package com.mhq.salati.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.SheetBackground

//@Composable
//fun SystemPermissionBanner(
//    message: String,
//    onActionClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier.fillMaxSize().background(Color.Black)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    SheetBackground,
//                    RoundedCornerShape(12.dp)
//                )
//                .border(
//                    1.dp,
//                    DarkGreen,
//                    RoundedCornerShape(12.dp)
//                )
//                .padding(
//                    horizontal = 16.dp,
//                    vertical = 12.dp
//                )
//        ) {
//            Icon(
//                imageVector = Icons.Default.Warning,
//                contentDescription = null,
//                tint = DarkGreen
//            )
//            Spacer(
//                modifier = Modifier.width(12.dp)
//            )
//            Text(
//                text = message,
//                modifier = Modifier.weight(1f),
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black
//            )
//            TextButton(
//                onClick = onActionClick
//            ) {
//                Text(text = "Enable")
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun SystemPermissionBannerPreview() {
//    SalatiTheme() {
//        SystemPermissionBanner(
//            message = "Some error message",
//            onActionClick = {}
//        )
//    }
//}