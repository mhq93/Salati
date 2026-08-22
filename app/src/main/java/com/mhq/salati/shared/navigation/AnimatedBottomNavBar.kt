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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.presentation.theme.AccentEmerald
import com.mhq.salati.shared.presentation.theme.AccentGold
import com.mhq.salati.shared.presentation.theme.DarkGreenLight
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun AnimatedBottomNavBar(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val unselectedContentColor = AccentEmerald
    val selectedContentColor = AccentGold

    val itemCount = items.size
    val density = LocalDensity.current
    var barWidthPx by remember { mutableIntStateOf(0) }

    val selectedIndex = items.indexOfFirst {
        it.route == selectedRoute
    }.coerceAtLeast(0)

    // --- RTL DETECTION ---
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl

    // Inverts the internal index calculation when the device layout is Right-to-Left
    val visualIndex = if (isRtl) itemCount - 1 - selectedIndex else selectedIndex

    val barHeight = 84.dp
    val notchRadius = 32.dp
    val bubbleSize = 60.dp
    val cornerRadius = 28.dp

    val horizontalEdgePadding = 16.dp
    val horizontalEdgePaddingPx = with(density) { horizontalEdgePadding.toPx() }

    val usableWidthPx = (barWidthPx - (horizontalEdgePaddingPx * 2f)).coerceAtLeast(0f)
    val itemWidthPx = if (itemCount > 0) usableWidthPx / itemCount else 0f

    // Calculate center using visualIndex so the canvas cuts out the right spot
    val targetCenterPx =
        horizontalEdgePaddingPx + (itemWidthPx * visualIndex) + (itemWidthPx / 2f)

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
        // --- BACKGROUND NOTCH CANVAS ---
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
                val cx = animatedCenterPx

                val notchStart = cx - notchRadiusPx * 1.6f
                val notchEnd = cx + notchRadiusPx * 1.6f

                // --- LEFT EDGE PROTECTION ---
                if (notchStart > cornerRadiusPx) {
                    moveTo(0f, cornerRadiusPx)
                    quadraticTo(0f, 0f, cornerRadiusPx, 0f)
                    lineTo(notchStart, 0f)
                } else {
                    moveTo(0f, cornerRadiusPx)
                    quadraticTo(0f, 0f, notchStart.coerceAtLeast(0f), 0f)
                }

                // --- THE NOTCH CURVE ---
                cubicTo(
                    cx - notchRadiusPx * 0.9f, 0f,
                    cx - notchRadiusPx * 0.85f, notchRadiusPx * 1.15f,
                    cx, notchRadiusPx * 1.15f
                )
                cubicTo(
                    cx + notchRadiusPx * 0.85f, notchRadiusPx * 1.15f,
                    cx + notchRadiusPx * 0.9f, 0f,
                    notchEnd, 0f
                )

                // --- RIGHT EDGE PROTECTION ---
                if (notchEnd < w - cornerRadiusPx) {
                    lineTo(w - cornerRadiusPx, 0f)
                    quadraticTo(w, 0f, w, cornerRadiusPx)
                } else {
                    quadraticTo(w, 0f, w, cornerRadiusPx)
                }

                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(
                path = path,
                color = DarkGreenLight
            )
        }

        // --- FLOATING SELECTION BUBBLE ---
        val bubbleSizePx = with(density) { bubbleSize.roundToPx() }
        val bubbleOffsetYPx = -(with(density) { (bubbleSize / 2.2f).roundToPx() })

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset {
                    // Compute center distance matching Compose's native start boundary rules
                    val centerFromStartPx = if (isRtl) barWidthPx - animatedCenterPx.toInt() else animatedCenterPx.toInt()
                    val leftEdgeFromStartPx = centerFromStartPx - (bubbleSizePx / 2)

                    IntOffset(
                        x = leftEdgeFromStartPx,
                        y = bubbleOffsetYPx
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
                tint = DarkGreenLight,
                modifier = Modifier.size(28.dp)
            )
        }

        // --- ALIGNED CONTENT ROW ---
        Row(
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
                    verticalArrangement = Arrangement.Bottom,
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
                        Spacer(modifier = Modifier.height(4.dp))
                    } else {
                        Spacer(Modifier.height(32.dp))
                    }

                    Text(
                        text = item.label,
                        color = if (isSelected) selectedContentColor else unselectedContentColor.copy(
                            alpha = 0.8f
                        ),
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
fun AnimatedBottomNavBarPreview() {
    val mockItems = listOf(
        BottomNavItem(
            label = "Home",
            route = "home",
            icon = Icons.Filled.CheckCircle
        ),
        BottomNavItem(
            label = "Settings",
            route = "settings",
            icon = Icons.Filled.CheckCircle
        )
    )

    SalatiTheme {
        AnimatedBottomNavBar(
            items = mockItems,
            selectedRoute = "home",
            onItemSelected = {}
        )
    }
}