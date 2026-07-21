package com.mhq.salati.presentation.qibla.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.domain.model.qibla.CompassAccuracy
import com.mhq.salati.presentation.theme.SalatiTheme

//@Composable
//fun CalibrationBanner(accuracy: CompassAccuracy) {
//    var showInstructions by remember { mutableStateOf(false) }
//
//    val (statusText, dotColor) = when (accuracy) {
//        CompassAccuracy.HIGH, CompassAccuracy.MEDIUM ->
//            "Your phone sensor accuracy is good" to Color(0xFF4CAF50)
//
//        CompassAccuracy.LOW ->
//            "Compass accuracy is low" to Color(0xFFFFC107)
//
//        CompassAccuracy.UNRELIABLE ->
//            "Compass accuracy is unreliable" to Color(0xFFF44336)
//    }
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier.fillMaxWidth().padding(16.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(10.dp)
//                    .background(
//                        color = dotColor,
//                        shape = RoundedCornerShape(
//                            50
//                        )
//                    )
//            )
//            Text(
//                text = statusText,
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier
//                    .padding(
//                        start = 8.dp
//                    )
//            )
//        }
//
//        if (accuracy == CompassAccuracy.LOW || accuracy == CompassAccuracy.UNRELIABLE) {
//            TextButton(onClick = { showInstructions = true }) {
//                Text("CALIBRATE")
//            }
//        }
//    }
//
//    if (showInstructions) {
//        Text(
//            text = "Move your phone in a figure-8 motion a few times to recalibrate the compass sensor.",
//            style = MaterialTheme.typography.bodySmall,
//            modifier = Modifier
//                .padding(
//                    top = 8.dp
//                )
//        )
//    }
//}

@Composable
fun CalibrationBanner(
    compassAccuracy: CompassAccuracy,
    onCalibrateClick: () -> Unit
) {
    val (label, dotColor) = when (compassAccuracy) {
        CompassAccuracy.HIGH -> "Your phone's sensor accuracy is quite reliable." to Color(0xFF4CAF50)
        CompassAccuracy.MEDIUM -> "Your phone's sensor accuracy is moderate." to Color(0xFFFFC107)
        else -> "Your phone's sensor accuracy is low." to Color(0xFFF44336)
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(dotColor, CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Text(text = label, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onCalibrateClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Please calibrate")
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
            onCalibrateClick = {}
        )
    }
}