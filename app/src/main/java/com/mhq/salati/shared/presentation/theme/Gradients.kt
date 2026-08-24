package com.mhq.salati.shared.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

val ColorScheme.prayerGradient: Brush
    @Composable get() = Brush.verticalGradient(
        listOf(primaryContainer, primary)
    )

val ColorScheme.countdownSweep: Brush
    @Composable get() = Brush.sweepGradient(
        listOf(tertiary, secondary, tertiary)
    )