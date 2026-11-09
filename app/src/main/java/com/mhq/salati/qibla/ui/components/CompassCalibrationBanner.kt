package com.mhq.salati.qibla.ui.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * The sensor-accuracy sentence and the colour of its status dot. Shared by the portrait banner and
 * the landscape card so both always say the same thing.
 */
@Composable
internal fun CompassAccuracyUi.sensorStatus(): Pair<String, Color> = when (this) {
    CompassAccuracyUi.HIGH ->
        stringResource(R.string.sensor_accuracy_is_excellent) to MaterialTheme.colorScheme.primary

    CompassAccuracyUi.MEDIUM ->
        stringResource(R.string.sensor_accuracy_is_moderate) to MaterialTheme.colorScheme.onSurfaceVariant

    else ->
        stringResource(R.string.sensor_accuracy_is_poor) to MaterialTheme.colorScheme.error
}

/**
 * Text styles come from the theme typography (not hard-coded sizes) so the Arabic-safe line
 * heights in Type.kt apply here too.
 */
@Composable
fun CalibrationBanner(
    compassAccuracy: CompassAccuracyUi,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (label, dotColor) = compassAccuracy.sensorStatus()

    Surface(
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 20.dp,
            bottomStart = 20.dp
        ),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(dotColor, CircleShape)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            TextButton(
                onClick = onRecalibrateClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.recalibrate),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
private fun CalibrationBannerPreview() {
    SalatiTheme {
        CalibrationBanner(
            compassAccuracy = CompassAccuracyUi.HIGH,
            onRecalibrateClick = {}
        )
    }
}

@Preview(locale = "ar", fontScale = 1.5f)
@Composable
private fun CalibrationBannerArabicLargeFontPreview() {
    SalatiTheme {
        CalibrationBanner(
            compassAccuracy = CompassAccuracyUi.MEDIUM,
            onRecalibrateClick = {}
        )
    }
}