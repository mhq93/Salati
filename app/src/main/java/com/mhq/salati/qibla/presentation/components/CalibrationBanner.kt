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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.qibla.domain.model.CompassAccuracy

@Composable
fun CalibrationBanner(
    compassAccuracy: CompassAccuracy,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (label, dotColor) = when (compassAccuracy) {
        CompassAccuracy.HIGH -> stringResource(R.string.sensor_accuracy_is_excellent) to MaterialTheme.colorScheme.primary
        CompassAccuracy.MEDIUM -> stringResource(R.string.sensor_accuracy_is_moderate) to MaterialTheme.colorScheme.tertiary
        else -> stringResource(R.string.sensor_accuracy_is_poor) to MaterialTheme.colorScheme.error
    }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface, // <-- Replaced SheetBackground
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) // <-- Replaced DarkGreen
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
                color = MaterialTheme.colorScheme.onSurface, // <-- Replaced InkText
                fontSize = 13.sp,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            TextButton(
                onClick = onRecalibrateClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary, // <-- Replaced AccentGold
                    contentColor = MaterialTheme.colorScheme.onTertiary // <-- Replaced DarkGreenLight
                )
            ) {
                Text(
                    text = stringResource(R.string.recalibrate),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

//package com.mhq.salati.qibla.presentation.components
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.R
//import com.mhq.salati.qibla.domain.model.CompassAccuracy
//import com.mhq.salati.shared.presentation.theme.AccentGold
//import com.mhq.salati.shared.presentation.theme.DarkGreen
//import com.mhq.salati.shared.presentation.theme.DarkGreenLight
//import com.mhq.salati.shared.presentation.theme.InkText
//import com.mhq.salati.shared.presentation.theme.MaterialAmber
//import com.mhq.salati.shared.presentation.theme.MaterialGreen
//import com.mhq.salati.shared.presentation.theme.MaterialRed
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//import com.mhq.salati.shared.presentation.theme.SheetBackground
//
//@Composable
//fun CalibrationBanner(
//    compassAccuracy: CompassAccuracy,
//    onRecalibrateClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val (label, dotColor) = when (compassAccuracy) {
//        CompassAccuracy.HIGH ->
//            stringResource(R.string.sensor_accuracy_is_excellent) to MaterialGreen
//        CompassAccuracy.MEDIUM ->
//            stringResource(R.string.sensor_accuracy_is_moderate) to MaterialAmber
//        else ->
//            stringResource(R.string.sensor_accuracy_is_poor) to MaterialRed
//    }
//
//    Surface(
//        shape = RoundedCornerShape(18.dp),
//        color = SheetBackground,
//        border = BorderStroke(
//            1.dp,
//            DarkGreen.copy(alpha = 0.08f)
//        ),
//        modifier = modifier.fillMaxWidth()
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .padding(
//                    horizontal = 16.dp,
//                    vertical = 14.dp
//                )
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(8.dp)
//                    .background(dotColor, CircleShape)
//            )
//            Spacer(
//                Modifier.width(10.dp)
//            )
//            Text(
//                text = label,
//                color = InkText,
//                fontSize = 13.sp,
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(
//                Modifier.width(8.dp)
//            )
//            TextButton(
//                onClick = onRecalibrateClick,
//                border = BorderStroke(1.dp, DarkGreen.copy(alpha = 0.08f)),
//                colors = ButtonDefaults.textButtonColors(
//                    containerColor = AccentGold,
//                    contentColor = DarkGreenLight
//                )
//            ) {
//                Text(
//                    text = stringResource(R.string.recalibrate),
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 13.sp
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun CalibrationBannerPreview() {
//    SalatiTheme() {
//        CalibrationBanner(
//            compassAccuracy = CompassAccuracy.HIGH,
//            onRecalibrateClick = {}
//        )
//    }
//}