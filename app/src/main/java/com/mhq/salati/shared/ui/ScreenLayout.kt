package com.mhq.salati.shared.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Horizontal margin shared by the tab header and the content of every tab screen. */
val ScreenHorizontalPadding = 24.dp

@Composable
fun isWideLayout(): Boolean {
    val c = LocalConfiguration.current
    return c.screenWidthDp > c.screenHeightDp
}

/**
 * The larger of the left and right display-cutout insets.
 * Applying it on BOTH sides keeps the margins symmetric whichever way the phone is rotated.
 * Zero in portrait.
 */
@Composable
fun symmetricCutoutInset(): Dp {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val cutout = WindowInsets.displayCutout
    return with(density) {
        maxOf(
            cutout.getLeft(density, layoutDirection),
            cutout.getRight(density, layoutDirection)
        ).toDp()
    }
}

/** Horizontal margin for tab headers and content:
 * the base padding plus the symmetric cutout inset. */
@Composable
fun screenHorizontalMargin(): Dp = ScreenHorizontalPadding + symmetricCutoutInset()