package com.mhq.salati.qibla.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mhq.salati.qibla.domain.model.CompassAccuracy
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassCalibrationOverlay(
    accuracy: CompassAccuracy,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "figure8")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart
        ),
        label = "figure8Progress"
    )

    // Extract colors BEFORE Canvas/DrawScope
    val scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.85f)
    val textColor = MaterialTheme.colorScheme.onSurface
    val textSecondaryColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
    val buttonBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    val (highLabel, highColor) = "Accuracy: Excellent" to MaterialTheme.colorScheme.primary
    val (mediumLabel, mediumColor) = "Accuracy: Moderate — Keep going" to MaterialTheme.colorScheme.tertiary
    val (lowLabel, lowColor) = "Accuracy: Poor — Keep moving in a figure-8 to recalibrate" to MaterialTheme.colorScheme.error
    val (unreliableLabel, unreliableColor) = "Accuracy: Unreliable — Move away from metal or magnets then recalibrate" to MaterialTheme.colorScheme.error

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(scrimColor) // <-- Replaced Color.Black.copy
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { /* absorb clicks */ },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Canvas(modifier = Modifier.size(160.dp)) {
                drawFigure8Path(
                    progress = progress,
                    pathColor = textColor.copy(alpha = 0.25f), // <-- Replaced Color.White.copy
                    dotColor = textColor // <-- Replaced Color.White
                )
            }

            Text(
                text = "Calibrate your compass",
                color = textColor, // <-- Replaced Color.White
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Move your phone in a figure-8 motion to improve accuracy.",
                color = textSecondaryColor, // <-- Replaced Color.White.copy(alpha = 0.75f)
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            AccuracyStatus(
                accuracy = accuracy,
                highLabel = highLabel, highColor = highColor,
                mediumLabel = mediumLabel, mediumColor = mediumColor,
                lowLabel = lowLabel, lowColor = lowColor,
                unreliableLabel = unreliableLabel, unreliableColor = unreliableColor
            )

            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor),
                border = BorderStroke(1.dp, buttonBorderColor) // <-- Replaced Color.White.copy
            ) {
                Text("Done")
            }
        }
    }
}

private fun DrawScope.drawFigure8Path(progress: Float, pathColor: Color, dotColor: Color) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f
    val rx = w / 2.4f
    val ry = h / 3.2f

    val path = Path().apply {
        val steps = 120
        for (i in 0..steps) {
            val t = (i / steps.toFloat()) * (2 * PI).toFloat()
            val x = cx + rx * sin(t)
            val y = cy + ry * sin(t) * cos(t)
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }

    drawPath(
        path,
        color = pathColor,
        style = Stroke(width = 3.dp.toPx())
    )

    val dotT = progress * (2 * PI).toFloat()
    val dotX = cx + rx * sin(dotT)
    val dotY = cy + ry * sin(dotT) * cos(dotT)

    drawCircle(
        color = dotColor,
        radius = 8.dp.toPx(),
        center = Offset(dotX, dotY)
    )
}

@Composable
private fun AccuracyStatus(
    accuracy: CompassAccuracy,
    highLabel: String, highColor: Color,
    mediumLabel: String, mediumColor: Color,
    lowLabel: String, lowColor: Color,
    unreliableLabel: String, unreliableColor: Color
) {
    val (label, color) = when (accuracy) {
        CompassAccuracy.HIGH -> highLabel to highColor
        CompassAccuracy.MEDIUM -> mediumLabel to mediumColor
        CompassAccuracy.LOW -> lowLabel to lowColor
        CompassAccuracy.UNRELIABLE -> unreliableLabel to unreliableColor
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, shape = CircleShape)
        )
        Text(text = label, color = color, style = MaterialTheme.typography.bodySmall)
    }
}

//package com.mhq.salati.qibla.presentation.components
//
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.animateFloat
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import com.mhq.salati.qibla.domain.model.CompassAccuracy
//import com.mhq.salati.shared.presentation.theme.MaterialAmber
//import com.mhq.salati.shared.presentation.theme.MaterialGreen
//import com.mhq.salati.shared.presentation.theme.MaterialRed
//import kotlin.math.cos
//import kotlin.math.sin
//
//@Composable
//fun CompassCalibrationOverlay(
//    accuracy: CompassAccuracy,
//    onDismiss: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val infiniteTransition = rememberInfiniteTransition(label = "figure8")
//    val progress by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2600, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        ),
//        label = "figure8Progress"
//    )
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color.Black.copy(alpha = 0.85f))
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null
//            ) { /* absorb clicks, no dismiss-on-scrim-tap by design */ },
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(24.dp),
//            modifier = Modifier.padding(32.dp)
//        ) {
//            Canvas(modifier = Modifier.size(160.dp)) {
//                drawFigure8Path(progress = progress)
//            }
//
//            Text(
//                text = "Calibrate your compass",
//                color = Color.White,
//                style = MaterialTheme.typography.titleMedium
//            )
//
//            Text(
//                text = "Move your phone in a figure-8 motion to improve accuracy.",
//                color = Color.White.copy(alpha = 0.75f),
//                style = MaterialTheme.typography.bodyMedium,
//                textAlign = TextAlign.Center
//            )
//
//            AccuracyStatus(accuracy)
//
//            OutlinedButton(
//                onClick = onDismiss,
//                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
//                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
//            ) {
//                Text("Done")
//            }
//        }
//    }
//}
//
//private fun DrawScope.drawFigure8Path(progress: Float) {
//    val w = size.width
//    val h = size.height
//    val cx = w / 2f
//    val cy = h / 2f
//    val rx = w / 2.4f
//    val ry = h / 3.2f
//
//    val path = Path().apply {
//        val steps = 120
//        for (i in 0..steps) {
//            val t = (i / steps.toFloat()) * (2 * Math.PI).toFloat()
//            val x = cx + rx * sin(t)
//            val y = cy + ry * sin(t) * cos(t)
//            if (i == 0) moveTo(x, y) else lineTo(x, y)
//        }
//        close()
//    }
//
//    drawPath(
//        path,
//        color = Color.White.copy(alpha = 0.25f),
//        style = Stroke(width = 3.dp.toPx())
//    )
//
//    val dotT = progress * (2 * Math.PI).toFloat()
//    val dotX = cx + rx * sin(dotT)
//    val dotY = cy + ry * sin(dotT) * cos(dotT)
//
//    drawCircle(
//        color = Color.White,
//        radius = 8.dp.toPx(),
//        center = Offset(dotX, dotY)
//    )
//}
//
//@Composable
//private fun AccuracyStatus(accuracy: CompassAccuracy) {
//    val (label, color) = when (accuracy) {
//        CompassAccuracy.HIGH -> "Accuracy: Excellent" to MaterialGreen
//        CompassAccuracy.MEDIUM -> "Accuracy: Moderate — Keep going" to MaterialAmber
//        CompassAccuracy.LOW -> "Accuracy: Poor — Keep moving in a figure-8 to recalibrate" to MaterialRed
//        CompassAccuracy.UNRELIABLE -> "Accuracy: Unreliable — Move away from metal or magnets then recalibrate" to Color(0xFFE0645C)
//    }
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(8.dp)
//                .background(color, shape = CircleShape)
//        )
//        Text(text = label, color = color, style = MaterialTheme.typography.bodySmall)
//    }
//}