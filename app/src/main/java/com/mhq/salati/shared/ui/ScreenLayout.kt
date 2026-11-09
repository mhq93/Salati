package com.mhq.salati.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isWideLayout(): Boolean {
    val c = LocalConfiguration.current
    return c.screenWidthDp > c.screenHeightDp
}