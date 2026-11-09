package com.mhq.salati.qibla.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.theme.SalatiTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Full-screen calibration guide.
 *
 * The scrim covers the whole screen, but the content is kept inside the safe drawing area (status
 * bar, display cutout, navigation bar) and above the floating bottom bar, which is drawn on top of
 * this overlay. Both layouts scroll when the window is too short (landscape, split-screen, large
 * font, Arabic).
 */
@Composable
fun CompassCalibrationOverlay(
    accuracy: CompassAccuracyUi,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isWide = isWideLayout()

    val infiniteTransition = rememberInfiniteTransition(label = "figure8")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "figure8Progress"
    )

    val scrimColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    val textColor = MaterialTheme.colorScheme.onSurface
    val textSecondaryColor = MaterialTheme.colorScheme.onSurfaceVariant
    val buttonBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    val (highLabel, highColor) = stringResource(R.string.accuracy_excellent) to MaterialTheme.colorScheme.primary
    val (mediumLabel, mediumColor) = stringResource(R.string.accuracy_moderate) to MaterialTheme.colorScheme.tertiary
    val (lowLabel, lowColor) = stringResource(R.string.accuracy_poor) to MaterialTheme.colorScheme.error
    val (unreliableLabel, unreliableColor) = stringResource(R.string.accuracy_unreliable) to MaterialTheme.colorScheme.error

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(scrimColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { /* absorb clicks */ }
            .safeDrawingPadding()
            .padding(bottom = BottomNavBarDefaults.Height),
    ) {
        if (isWide) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(CompassWideLayout.Padding),
                horizontalArrangement = Arrangement.spacedBy(CompassWideLayout.Spacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(CompassWideLayout.DialPaneWeight),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(130.dp)) {
                        drawFigure8Path(
                            progress = progress,
                            pathColor = textColor.copy(alpha = 0.25f),
                            dotColor = textColor
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(CompassWideLayout.SidePaneWeight)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.compass_calibrate_title),
                        color = textColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(R.string.compass_calibrate_body),
                        color = textSecondaryColor,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
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
                        border = BorderStroke(1.dp, buttonBorderColor),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(stringResource(R.string.compass_calibrate_done))
                    }
                }
            }
        } else {
            /** Centered by the parent Box when it fits; scrolls when it does not. */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp)
            ) {
                Canvas(modifier = Modifier.size(160.dp)) {
                    drawFigure8Path(
                        progress = progress,
                        pathColor = textColor.copy(alpha = 0.25f),
                        dotColor = textColor
                    )
                }

                Text(
                    text = stringResource(R.string.compass_calibrate_title),
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(R.string.compass_calibrate_body),
                    color = textSecondaryColor,
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
                    border = BorderStroke(1.dp, buttonBorderColor)
                ) {
                    Text(stringResource(R.string.compass_calibrate_done))
                }
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
    accuracy: CompassAccuracyUi,
    highLabel: String, highColor: Color,
    mediumLabel: String, mediumColor: Color,
    lowLabel: String, lowColor: Color,
    unreliableLabel: String, unreliableColor: Color
) {
    val (label, color) = when (accuracy) {
        CompassAccuracyUi.HIGH -> highLabel to highColor
        CompassAccuracyUi.MEDIUM -> mediumLabel to mediumColor
        CompassAccuracyUi.LOW -> lowLabel to lowColor
        CompassAccuracyUi.UNRELIABLE -> unreliableLabel to unreliableColor
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
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(name = "Light Mode Overlay", showSystemUi = true)
@Preview(name = "Dark Mode Overlay", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Landscape Overlay", widthDp = 800, heightDp = 360)
@Preview(name = "Arabic Large Font", locale = "ar", fontScale = 1.5f, showSystemUi = true)
@Composable
private fun CompassCalibrationOverlayPreview() {
    SalatiTheme {
        CompassCalibrationOverlay(
            accuracy = CompassAccuracyUi.LOW,
            onDismiss = {}
        )
    }
}