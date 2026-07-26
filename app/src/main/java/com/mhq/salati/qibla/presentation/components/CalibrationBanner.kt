package com.mhq.salati.qibla.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.InkText
import com.mhq.salati.shared.presentation.theme.MaterialAmber
import com.mhq.salati.shared.presentation.theme.MaterialGreen
import com.mhq.salati.shared.presentation.theme.MaterialRed
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.SheetBackground

@Composable
fun CalibrationBanner(
    compassAccuracy: CompassAccuracy,
    onRecalibrateClick: () -> Unit
) {
    val (label, dotColor) = when (compassAccuracy) {
        CompassAccuracy.HIGH ->
            "Your phone's sensor accuracy is excellent." to MaterialGreen
        CompassAccuracy.MEDIUM ->
            "Your phone's sensor accuracy is moderate." to MaterialAmber
        else ->
            "Your phone's sensor accuracy is poor." to MaterialRed
    }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = SheetBackground,
        border = BorderStroke(1.dp, DarkGreen.copy(alpha = 0.08f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(dotColor, CircleShape)
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = label,
                color = InkText,
                fontSize = 13.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            TextButton(
                onClick = onRecalibrateClick,
                colors = ButtonDefaults.textButtonColors(contentColor = DarkGreen)
            ) {
                Text(
                    text = "Recalibrate",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun CalibrationBannerPreview() {
    SalatiTheme() {
        CalibrationBanner(
            compassAccuracy = CompassAccuracy.HIGH,
            onRecalibrateClick = {}
        )
    }
}