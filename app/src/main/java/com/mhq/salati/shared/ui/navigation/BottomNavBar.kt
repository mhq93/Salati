package com.mhq.salati.shared.ui.navigation

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.theme.SalatiTheme

private val NotchRadius = 32.dp
private val BubbleSize = 60.dp
private val CornerRadius = 28.dp
private val MinHorizontalEdgePadding = 16.dp
private const val NotchHalfWidthFactor = 1.35f

@Composable
fun BottomNavBar(
    items: List<BottomNavBarItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLandscape = isWideLayout()

    val bubbleIconColor = MaterialTheme.colorScheme.primary
    val barBackgroundColor = MaterialTheme.colorScheme.primaryContainer
    val selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    val unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)

    val leftGradientEndColor = MaterialTheme.colorScheme.primary
    val rightSurfaceColor = MaterialTheme.colorScheme.background

    val itemCount = items.size
    val density = LocalDensity.current
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val selectedIndex = items.indexOfFirst { it.route == selectedRoute }.coerceAtLeast(0)
    val visualIndex = if (isRtl) itemCount - 1 - selectedIndex else selectedIndex

    val minEdgePaddingPx = with(density) { MinHorizontalEdgePadding.toPx() }
    val notchRadiusPx = with(density) { NotchRadius.toPx() }
    val cornerRadiusPx = with(density) { CornerRadius.toPx() }
    val notchHalfWidthPx = notchRadiusPx * NotchHalfWidthFactor
    val bubbleSizePx = with(density) { BubbleSize.roundToPx() }

    val notchDepthPx = notchRadiusPx * 1.15f
    val bubbleOffsetYPx = (notchDepthPx / 2f - (bubbleSizePx / 2f)).toInt()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        val barWidthPx = constraints.maxWidth

        val minEdgeCenterPx = cornerRadiusPx + notchHalfWidthPx
        val edgePaddingPx = if (itemCount > 1) {
            val solved = (minEdgeCenterPx - barWidthPx / (2f * itemCount)) / (1f - 1f / itemCount)
            solved.coerceIn(minEdgePaddingPx, (barWidthPx / 4f).coerceAtLeast(minEdgePaddingPx))
        } else {
            minEdgePaddingPx
        }
        val edgePaddingDp = with(density) { edgePaddingPx.toDp() }

        val usableWidthPx = (barWidthPx - (edgePaddingPx * 2f)).coerceAtLeast(0f)
        val itemWidthPx = if (itemCount > 0) usableWidthPx / itemCount else 0f
        val targetCenterPx = edgePaddingPx + (itemWidthPx * visualIndex) + (itemWidthPx / 2f)

        val animatedCenterPx by animateFloatAsState(
            targetValue = targetCenterPx,
            animationSpec = spring(dampingRatio = 0.8f, stiffness = Spring.StiffnessMedium),
            label = "notchCenter"
        )

        val bubbleScale = remember { Animatable(1f) }
        var previousIndex by remember { mutableIntStateOf(selectedIndex) }
        LaunchedEffect(selectedIndex) {
            if (previousIndex != selectedIndex) {
                previousIndex = selectedIndex
                bubbleScale.snapTo(0.6f)
                bubbleScale.animateTo(
                    1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            }
        }

        val notchPath = remember { Path() }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomNavBarDefaults.Height)
        ) {
            val w = size.width
            val h = size.height
            val cx = animatedCenterPx.coerceIn(
                minEdgeCenterPx,
                maxOf(minEdgeCenterPx, w - minEdgeCenterPx)
            )
            val notchStart = cx - notchHalfWidthPx
            val notchEnd = cx + notchHalfWidthPx

            // ONLY apply split backdrop treatment if on Home screen in landscape
            if (isLandscape && selectedRoute == NavigationScreen.Home.route) {
                drawRect(
                    color = if (isRtl) rightSurfaceColor else leftGradientEndColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(w / 2f, h)
                )
                drawRect(
                    color = if (isRtl) leftGradientEndColor else rightSurfaceColor,
                    topLeft = Offset(w / 2f, 0f),
                    size = Size(w / 2f, h)
                )
            } else if (isLandscape) {
                // For Qibla and all other views, fill the standard full width surface color behind the notch curves
                drawRect(color = rightSurfaceColor, topLeft = Offset(0f, 0f), size = Size(w, h))
            }

            notchPath.rewind()
            notchPath.apply {
                moveTo(0f, cornerRadiusPx)
                quadraticTo(0f, 0f, cornerRadiusPx, 0f)
                lineTo(notchStart, 0f)
                cubicTo(
                    cx - notchHalfWidthPx * 0.56f, 0f,
                    cx - notchHalfWidthPx * 0.53f, notchDepthPx,
                    cx, notchDepthPx
                )
                cubicTo(
                    cx + notchHalfWidthPx * 0.53f, notchDepthPx,
                    cx + notchHalfWidthPx * 0.56f, 0f,
                    notchEnd, 0f
                )
                lineTo(w - cornerRadiusPx, 0f)
                quadraticTo(w, 0f, w, cornerRadiusPx)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(path = notchPath, color = barBackgroundColor)
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset {
                    val centerFromStartPx =
                        if (isRtl) barWidthPx - animatedCenterPx.toInt() else animatedCenterPx.toInt()
                    IntOffset(x = centerFromStartPx - (bubbleSizePx / 2), y = bubbleOffsetYPx)
                }
                .size(BubbleSize)
                .graphicsLayer {
                    scaleX = bubbleScale.value
                    scaleY = bubbleScale.value
                }
                .background(selectedContentColor, CircleShape)
                .clearAndSetSemantics { }
        ) {
            items.getOrNull(selectedIndex)?.let { selectedItem ->
                Icon(
                    imageVector = selectedItem.icon,
                    contentDescription = null,
                    tint = bubbleIconColor,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomNavBarDefaults.Height)
                .padding(start = edgePaddingDp, end = edgePaddingDp, bottom = 12.dp)
        ) {
            items.forEachIndexed { index, item ->
                BottomNavItem(
                    item = item,
                    isSelected = index == selectedIndex,
                    selectedColor = selectedContentColor,
                    unselectedColor = unselectedContentColor,
                    onClick = { onItemSelected(item.route) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
@Composable
private fun RowScope.BottomNavItem(
    item: BottomNavBarItem,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = tween(80),
        label = "navItemPress"
    )
    val iconAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0f else 0.7f,
        label = "navIconAlpha"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 0.5f else 1f,
        label = "navIconScale"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxHeight()
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
            }
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
    ) {
        Box(modifier = Modifier.size(28.dp), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = unselectedColor,
                modifier = Modifier
                    .size(26.dp)
                    .graphicsLayer {
                        alpha = iconAlpha
                        scaleX = iconScale
                        scaleY = iconScale
                    }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            color = if (isSelected) selectedColor else unselectedColor.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            softWrap = false
        )
    }
}
@Preview
@Composable
private fun BottomNavBarPreview() {
    val mockItems = listOf(
        BottomNavBarItem("home", "Home", Icons.Filled.CheckCircle),
        BottomNavBarItem("settings", "Settings", Icons.Filled.CheckCircle)
    )
    SalatiTheme {
        BottomNavBar(items = mockItems, selectedRoute = "home", onItemSelected = {})
    }
}