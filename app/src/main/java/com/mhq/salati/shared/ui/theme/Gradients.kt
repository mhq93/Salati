package com.mhq.salati.shared.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val ColorScheme.prayerGradient: Brush
    @Composable get() = Brush.verticalGradient(
        listOf(
            primaryContainer,
            primary
        )
    )

val ColorScheme.countdownSweep: Brush
    @Composable get() = Brush.sweepGradient(
        listOf(
            tertiary.copy(alpha = 0.55f),
            tertiary,
            tertiary.copy(alpha = 0.55f)
        )
    )

val ColorScheme.countdownTrack: Color
    @Composable get() = onPrimary.copy(alpha = 0.18f)