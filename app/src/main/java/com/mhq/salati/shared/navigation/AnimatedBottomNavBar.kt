package com.mhq.salati.shared.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.presentation.theme.AccentOrange
import com.mhq.salati.shared.presentation.theme.DarkGreenLight
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun AnimatedBottomNavBar(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val unselectedContentColor = Color(0xFF1E352F)
    val selectedContentColor = AccentOrange

    val itemCount = items.size
    val density = LocalDensity.current
    var barWidthPx by remember { mutableIntStateOf(0) }

    val selectedIndex = items.indexOfFirst {
        it.route == selectedRoute
    }.coerceAtLeast(0)

    val barHeight = 84.dp
    val notchRadius = 32.dp
    val bubbleSize = 60.dp
    val cornerRadius = 28.dp

    // Safety thresholds to clear the rounded outer background tracks cleanly
    val horizontalEdgePadding = 16.dp
    val horizontalEdgePaddingPx = with(density) { horizontalEdgePadding.toPx() }

    // Math calculation adjustments calibrated to account for custom safety paddings
    val usableWidthPx = (barWidthPx - (horizontalEdgePaddingPx * 2f)).coerceAtLeast(0f)
    val itemWidthPx = if (itemCount > 0) usableWidthPx / itemCount else 0f

    // Anchor target tracks from padded start layout offset margins
    val targetCenterPx =
        horizontalEdgePaddingPx + (itemWidthPx * selectedIndex) + (itemWidthPx / 2f)

    val animatedCenterPx by animateFloatAsState(
        targetValue = targetCenterPx,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "notchCenter"
    )

    val bubbleScale = remember { Animatable(1f) }
    LaunchedEffect(selectedIndex) {
        bubbleScale.snapTo(0.6f)
        bubbleScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .onGloballyPositioned { barWidthPx = it.size.width }
        ) {
            val notchRadiusPx = notchRadius.toPx()
            val cornerRadiusPx = cornerRadius.toPx()

            val path = Path().apply {
                val w = size.width
                val h = size.height
                val minCx = notchRadiusPx * 1.6f + cornerRadiusPx
                val maxCx = w - notchRadiusPx * 1.6f - cornerRadiusPx
                val cx = animatedCenterPx.coerceIn(minCx, maxCx)

                moveTo(0f, cornerRadiusPx)
                quadraticTo(0f, 0f, cornerRadiusPx, 0f)

                lineTo(cx - notchRadiusPx * 1.6f, 0f)
                cubicTo(
                    cx - notchRadiusPx * 0.9f, 0f,
                    cx - notchRadiusPx * 0.85f, notchRadiusPx * 1.15f,
                    cx, notchRadiusPx * 1.15f
                )
                cubicTo(
                    cx + notchRadiusPx * 0.85f, notchRadiusPx * 1.15f,
                    cx + notchRadiusPx * 0.9f, 0f,
                    cx + notchRadiusPx * 1.6f, 0f
                )

                lineTo(w - cornerRadiusPx, 0f)
                quadraticTo(w, 0f, w, cornerRadiusPx)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }

//            drawContext.canvas.nativeCanvas.apply {
//                val shadowPaint = android.graphics.Paint().apply {
//                    color = android.graphics.Color.BLACK
//                    alpha = 12//35
//                    setShadowLayer(
//                        6.dp.toPx(), 0f, 4.dp.toPx(),//12
//                        android.graphics.Color.BLACK
//                    )
//                }
//                drawPath(path.asAndroidPath(), shadowPaint)
//            }

            drawPath(
                path = path,
                color = DarkGreenLight
            )
        }

        val clampedCenterPx = if (barWidthPx > 0) {
            val notchRadiusPx = with(density) { (notchRadius * 1.6f).toPx() }
            val cornerRadiusPx = with(density) { cornerRadius.toPx() }
            animatedCenterPx.coerceIn(
                notchRadiusPx + cornerRadiusPx,
                barWidthPx - notchRadiusPx - cornerRadiusPx
            )
        } else {
            0f
        }

        val bubbleOffsetX = with(density) {
            (clampedCenterPx.toInt() - (bubbleSize / 2).roundToPx())
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = bubbleOffsetX,
                        y = -(with(density) { (bubbleSize / 2.2f).roundToPx() })
                    )
                }
                .size(bubbleSize)
                .graphicsLayer(
                    scaleX = bubbleScale.value,
                    scaleY = bubbleScale.value
                )
                .background(selectedContentColor, CircleShape)
        ) {
            Icon(
                imageVector = items[selectedIndex].icon,
                contentDescription = items[selectedIndex].label,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        // --- ALIGNED CONTENT ROW ---
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .padding(
                    start = horizontalEdgePadding,
                    end = horizontalEdgePadding,
                    bottom = 12.dp
                )
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex

                val iconAlpha by animateFloatAsState(
                    if (isSelected) 0f else 0.7f,
                    label = "iconAlpha"
                )
                val iconScale by animateFloatAsState(
                    if (isSelected) 0.5f else 1f,
                    label = "iconScale"
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onItemSelected(item.route) }
                ) {
                    if (!isSelected) {
                        Box(
                            modifier = Modifier.size(28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = unselectedContentColor.copy(alpha = iconAlpha),
                                modifier = Modifier
                                    .size(26.dp)
                                    .graphicsLayer(
                                        scaleX = iconScale,
                                        scaleY = iconScale
                                    )
                            )
                        }
                    } else {
                        Spacer(Modifier.height(8.dp))
                    }

                    Text(
                        text = item.label,
                        color =
                            if (isSelected)
                                selectedContentColor
                            else
                                unselectedContentColor.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AnimatedBottomNavBarPreview() {
    SalatiTheme() {
        AnimatedBottomNavBar(
            items = emptyList(),
            selectedRoute = "",
            onItemSelected = {}
        )
    }
}