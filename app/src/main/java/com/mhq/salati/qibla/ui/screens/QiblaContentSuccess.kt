package com.mhq.salati.qibla.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.qibla.ui.components.CalibrationBanner
import com.mhq.salati.qibla.ui.components.CompassWideLayout
import com.mhq.salati.qibla.ui.components.RotatingCompassDial
import com.mhq.salati.qibla.ui.components.sensorStatus
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme

private val NarrowCardMargin = 16.dp
private val NarrowCardMaxWidth = 560.dp

/**
 * Height of everything in the narrow card except the dial (emoji, spacings, banner, margins).
 * Only used to decide how big the dial may be so the card fits without scrolling when it can.
 */
private val NarrowChromeHeight = 200.dp

private val MinDialSize = 180.dp
private val MaxDialSize = 400.dp

/**
 * Compass content for a loaded Qibla bearing.
 *
 * - Wide windows (landscape, tablets, foldables): dial on the start side, status card on the end.
 * - Narrow windows: one centered card; the dial is sized from the space actually available, and
 *   the column scrolls if a very short window or a large font still does not fit.
 *
 * Horizontal display-cutout insets are applied here so nothing sits under a notch in landscape.
 */
@Composable
fun QiblaContentSuccess(
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentModifier = modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))

    if (isWideLayout()) {
        QiblaWideContent(
            deviceHeading = deviceHeading,
            qiblaBearing = qiblaBearing,
            compassAccuracy = compassAccuracy,
            onRecalibrateClick = onRecalibrateClick,
            modifier = contentModifier
        )
    } else {
        QiblaNarrowContent(
            deviceHeading = deviceHeading,
            qiblaBearing = qiblaBearing,
            compassAccuracy = compassAccuracy,
            onRecalibrateClick = onRecalibrateClick,
            modifier = contentModifier
        )
    }
}

@Composable
private fun QiblaWideContent(
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(CompassWideLayout.Spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(CompassWideLayout.Padding)
    ) {
        // The dial takes the largest square that fits this pane (5% left for its shadow).
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(CompassWideLayout.DialPaneWeight)
                .fillMaxHeight()
        ) {
            RotatingCompassDial(
                deviceHeading = deviceHeading,
                qiblaBearing = qiblaBearing,
                modifier = Modifier.size(minOf(maxWidth, maxHeight) * 0.95f)
            )
        }

        QiblaCard(
            modifier = Modifier
                .weight(CompassWideLayout.SidePaneWeight)
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                val (label, dotColor) = compassAccuracy.sensorStatus()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(dotColor, CircleShape)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(Modifier.height(24.dp))

                TextButton(
                    onClick = onRecalibrateClick,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = stringResource(R.string.recalibrate),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun QiblaNarrowContent(
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val cardWidth = minOf(maxWidth - NarrowCardMargin * 2, NarrowCardMaxWidth)
        // Limited by width first (as before), then by the height that is really left.
        val dialSize = minOf(cardWidth * 0.85f - 16.dp, maxHeight - NarrowChromeHeight)
            .coerceIn(MinDialSize, MaxDialSize)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(NarrowCardMargin)
        ) {
            QiblaCard(
                modifier = Modifier
                    .widthIn(max = NarrowCardMaxWidth)
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "\uD83D\uDD4B",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontSize = 40.sp,
                            lineHeight = 48.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    RotatingCompassDial(
                        deviceHeading = deviceHeading,
                        qiblaBearing = qiblaBearing,
                        modifier = Modifier.size(dialSize)
                    )
                    Spacer(Modifier.height(24.dp))
                }
                CalibrationBanner(
                    compassAccuracy = compassAccuracy,
                    onRecalibrateClick = onRecalibrateClick
                )
            }
        }
    }
}

/** The one card style used by both layouts. */
@Composable
private fun QiblaCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        modifier = modifier,
        content = content
    )
}

@Preview(name = "Portrait", showSystemUi = true)
@Preview(name = "Portrait short", widthDp = 360, heightDp = 480)
@Preview(name = "Landscape", widthDp = 800, heightDp = 360)
@Preview(name = "Tablet", widthDp = 1024, heightDp = 768)
@Preview(name = "Arabic Large Font", locale = "ar", fontScale = 1.5f, showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QiblaContentSuccessPreview() {
    SalatiTheme {
        QiblaContentSuccess(
            deviceHeading = 1.0f,
            qiblaBearing = 1.0f,
            compassAccuracy = CompassAccuracyUi.HIGH,
            onRecalibrateClick = {}
        )
    }
}