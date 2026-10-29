package com.mhq.salati.shared.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val ColorScheme.prayerGradient: Brush
    @Composable get() = Brush.verticalGradient(
        listOf(
            primaryContainer,
            primary
        )
    )

/**
 * Background for the tab headers (Alarms, Tracker, Qibla, Settings).
 *
 * In dark mode `primary` is a bright emerald, which makes gold and white text unreadable at the
 * bottom of [prayerGradient]. Here the gradient ends in the deep secondary container instead, so
 * header text keeps its contrast across the whole header. Light mode is unchanged.
 */
val ColorScheme.tabHeaderGradient: Brush
    @Composable get() {
        val isDark = background.luminance() < 0.5f
        return Brush.verticalGradient(
            listOf(
                primaryContainer,
                if (isDark) secondaryContainer else primary
            )
        )
    }

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

//package com.mhq.salati.shared.ui.theme
//
//import androidx.compose.material3.ColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//
//val ColorScheme.prayerGradient: Brush
//    @Composable get() = Brush.verticalGradient(
//        listOf(
//            primaryContainer,
//            primary
//        )
//    )
//
//val ColorScheme.countdownSweep: Brush
//    @Composable get() = Brush.sweepGradient(
//        listOf(
//            tertiary.copy(alpha = 0.55f),
//            tertiary,
//            tertiary.copy(alpha = 0.55f)
//        )
//    )
//
//val ColorScheme.countdownTrack: Color
//    @Composable get() = onPrimary.copy(alpha = 0.18f)