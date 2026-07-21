package com.mhq.salati.presentation.navigation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.presentation.theme.AccentOrange
import com.mhq.salati.presentation.theme.HeaderGreenLight
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun AnimatedBottomNavBar(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val itemCount = items.size
    val density = LocalDensity.current
    var barWidthPx by remember { mutableIntStateOf(0) }


    val selectedIndex = items.indexOfFirst {
        it.route == selectedRoute
    }.coerceAtLeast(0)

    val itemWidthPx = if (itemCount > 0) barWidthPx / itemCount else 0
    val targetCenterPx = itemWidthPx * selectedIndex + itemWidthPx / 2

    val animatedCenterPx by animateFloatAsState(
        targetValue = targetCenterPx.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "notchCenter"
    )

    //val barHeight = 88.dp
    //val notchRadius = 34.dp
    //val bubbleSize = 52.dp

    val barHeight = 88.dp
    val notchRadius = 32.dp
    val bubbleSize = 56.dp

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
            val cornerRadiusPx = 24.dp.toPx()
            //val cornerRadiusPx = 28.dp.toPx()

            val path = Path().apply {
                val w = size.width
                val h = size.height
                val minCx = notchRadiusPx * 1.6f + cornerRadiusPx
                val maxCx = w - notchRadiusPx * 1.6f - cornerRadiusPx
                val cx = animatedCenterPx.coerceIn(
                    minCx,
                    maxCx
                )

                moveTo(0f, cornerRadiusPx)
                quadraticTo(0f, 0f, cornerRadiusPx, 0f)

                lineTo(cx - notchRadiusPx * 1.6f, 0f)
                cubicTo(
                    cx - notchRadiusPx * 0.9f, 0f,
                    cx - notchRadiusPx * 0.9f, notchRadiusPx * 1.15f,
                    cx, notchRadiusPx * 1.15f
                )
                cubicTo(
                    cx + notchRadiusPx * 0.9f, notchRadiusPx * 1.15f,
                    cx + notchRadiusPx * 0.9f, 0f,
                    cx + notchRadiusPx * 1.6f, 0f
                )

                lineTo(w - cornerRadiusPx, 0f)
                quadraticTo(w, 0f, w, cornerRadiusPx)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }

            drawPath(
                path = path,
                color = HeaderGreenLight
            )
        }

        val clampedCenterPx = if (barWidthPx > 0) {
            val notchRadiusPx = with(density) { (notchRadius * 1.6f).toPx() }
            val cornerRadiusPx = with(density) { 24.dp.toPx() }
            //val cornerRadiusPx = with(density) { 28.dp.toPx() }
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
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = bubbleOffsetX,
                        y = -(with(density) { (bubbleSize / 2).roundToPx() })
                    )
                }
                .size(bubbleSize)
                .background(AccentOrange, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = items[selectedIndex].icon,
                contentDescription = items[selectedIndex].label,
                tint = Color.White,
                modifier = Modifier.size(24.dp)//22
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .padding(top = 32.dp),//28
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onItemSelected(item.route) }
                ) {
                    if (!isSelected) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(24.dp)//22
                        )
                        Spacer(Modifier.height(4.dp))
                    } else {
                        Spacer(Modifier.height(24.dp))//26
                    }
                    Text(
                        text = item.label,
                        color = if (isSelected) AccentOrange else Color.White.copy(alpha = 0.6f),
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
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