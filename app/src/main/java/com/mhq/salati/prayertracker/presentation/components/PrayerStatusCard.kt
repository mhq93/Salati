package com.mhq.salati.prayertracker.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun PrayerStatusCard(
    prayer: PrayerName,
    status: PrayerStatus,
    locked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && !locked) 0.96f else 1f,
        animationSpec = tween(120),
        label = "cardScale"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .shadow(
                elevation = if (locked) 0.dp else 3.dp,
                shape = RoundedCornerShape(18.dp),
                clip = false,
                ambientColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), // <-- Replaced InkText
                spotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f) // <-- Replaced InkText
            )
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface) // <-- Replaced CardBackground
            .then(
                if (!locked) Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f), // <-- Replaced InkText
                    shape = RoundedCornerShape(18.dp)
                ) else Modifier
            )
            .clickable(
                enabled = !locked,
                interactionSource = interactionSource,
                indication = ripple(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)), // <-- Replaced InkText
                onClick = onClick
            )
            .padding(horizontal = 6.dp, vertical = 14.dp)
    ) {
        Text(
            text = stringResource(prayer.labelRes),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (locked) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface, // <-- Replaced MutedSlate / InkText
            maxLines = 1
        )

        AnimatedContent(
            targetState = status,
            transitionSpec = {
                (scaleIn(initialScale = 0.7f, animationSpec = tween(200)) + fadeIn(tween(200)))
                    .togetherWith(fadeOut(tween(120)))
            },
            label = "statusBadge"
        ) { animatedStatus ->
            when (animatedStatus) {
                PrayerStatus.PRAYED -> StatusBadge(
                    icon = Icons.Filled.Check,
                    color = MaterialTheme.colorScheme.primary // <-- Replaced Emerald
                )
                PrayerStatus.MISSED -> StatusBadge(
                    icon = Icons.Filled.Close,
                    color = MaterialTheme.colorScheme.error // <-- Replaced TomatoRed
                )
                PrayerStatus.PENDING -> Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.14f), // <-- Replaced MutedSlate
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.22f) // <-- Replaced MutedSlate
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.25f), // <-- Replaced MutedSlate
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrayerStatusCardPreview() {
    SalatiTheme {
        PrayerStatusCard(
            prayer = PrayerName.FAJR,
            status = PrayerStatus.PRAYED,
            locked = true,
            onClick = {}
        )
    }
}

//package com.mhq.salati.prayertracker.presentation.components
//
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.scaleIn
//import androidx.compose.animation.togetherWith
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.interaction.collectIsPressedAsState
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.Text
//import androidx.compose.material3.ripple
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mhq.salati.prayertracker.domain.model.PrayerStatus
//import com.mhq.salati.shared.domain.PrayerName
//import com.mhq.salati.shared.presentation.theme.CardBackground
//import com.mhq.salati.shared.presentation.theme.Emerald
//import com.mhq.salati.shared.presentation.theme.InkText
//import com.mhq.salati.shared.presentation.theme.MutedSlate
//import com.mhq.salati.shared.presentation.theme.SalatiTheme
//import com.mhq.salati.shared.presentation.theme.TomatoRed
//
//@Composable
//fun PrayerStatusCard(
//    prayer: PrayerName,
//    status: PrayerStatus,
//    locked: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//    val isPressed by interactionSource.collectIsPressedAsState()
//    val scale by animateFloatAsState(
//        targetValue = if (isPressed && !locked) 0.96f else 1f,
//        animationSpec = tween(120),
//        label = "cardScale"
//    )
//
//    Column(
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .graphicsLayer {
//                scaleX = scale
//                scaleY = scale
//            }
//            .shadow(
//                elevation = if (locked) 0.dp else 3.dp,
//                shape = RoundedCornerShape(18.dp),
//                clip = false,
//                ambientColor = InkText.copy(alpha = 0.08f),
//                spotColor = InkText.copy(alpha = 0.08f)
//            )
//            .clip(RoundedCornerShape(18.dp))
//            .background(CardBackground)
//            .then(
//                if (!locked) Modifier.border(
//                    width = 1.dp,
//                    color = InkText.copy(alpha = 0.04f),
//                    shape = RoundedCornerShape(18.dp)
//                ) else Modifier
//            )
//            .clickable(
//                enabled = !locked,
//                interactionSource = interactionSource,
//                indication = ripple(color = InkText.copy(alpha = 0.15f)),
//                onClick = onClick
//            )
//            .padding(horizontal = 6.dp, vertical = 14.dp)
//    ) {
//        Text(
//            text = stringResource(prayer.labelRes),
//            fontSize = 14.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = if (locked) MutedSlate.copy(alpha = 0.6f) else InkText,
//            maxLines = 1
//        )
//
//        AnimatedContent(
//            targetState = status,
//            transitionSpec = {
//                (scaleIn(initialScale = 0.7f, animationSpec = tween(200)) + fadeIn(tween(200)))
//                    .togetherWith(fadeOut(tween(120)))
//            },
//            label = "statusBadge"
//        ) { animatedStatus ->
//            when (animatedStatus) {
//                PrayerStatus.PRAYED -> StatusBadge(
//                    icon = Icons.Filled.Check,
//                    color = Emerald
//                )
//
//                PrayerStatus.MISSED -> StatusBadge(
//                    icon = Icons.Filled.Close,
//                    color = TomatoRed
//                )
//
//                PrayerStatus.PENDING -> Box(
//                    modifier = Modifier
//                        .size(28.dp)
//                        .clip(CircleShape)
//                        .background(
//                            Brush.linearGradient(
//                                colors = listOf(
//                                    MutedSlate.copy(alpha = 0.14f),
//                                    MutedSlate.copy(alpha = 0.22f)
//                                )
//                            )
//                        )
//                        .border(
//                            width = 1.dp,
//                            color = MutedSlate.copy(alpha = 0.25f),
//                            shape = CircleShape
//                        )
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun PrayerStatusCardPreview() {
//    SalatiTheme() {
//        PrayerStatusCard(
//            prayer = PrayerName.FAJR,
//            status = PrayerStatus.PRAYED,
//            locked = true,
//            onClick = {}
//        )
//    }
//}