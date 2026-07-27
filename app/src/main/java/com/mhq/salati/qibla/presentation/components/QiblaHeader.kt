package com.mhq.salati.qibla.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.DarkGreenLight
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun QiblaHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(DarkGreenLight, DarkGreen)
                )
            )
            .statusBarsPadding()
            .padding(
                bottom = 32.dp,
                start = 24.dp,
                end = 24.dp
            )
    ) {
        Column {
        //            Box(
        //                contentAlignment = Alignment.Center,
        //                modifier = Modifier
        //                    .size(56.dp)
        //                    .clip(RoundedCornerShape(16.dp))
        //                    .background(Color.White.copy(alpha = 0.12f)),
        //            ) {
        //                Icon(
        //                    imageVector = Icons.Default.Explore,
        //                    tint = AccentGold,
        //                    contentDescription = null,
        //                    modifier = Modifier.size(28.dp)
        //                )
        //            }
            Spacer(
                Modifier.height(16.dp)
            )
            Text(
                text = stringResource(R.string.qibla_direction),
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                Modifier.height(4.dp)
            )
            Text(
                text = stringResource(R.string.face_the_kaaba_wherever_you_are),
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
private fun QiblaHeaderPreview() {
    SalatiTheme() {
        QiblaHeader()
    }
}