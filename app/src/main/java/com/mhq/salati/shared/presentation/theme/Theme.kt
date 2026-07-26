package com.mhq.salati.shared.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
//
//@Composable
//fun SalatiTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}

private val LightColors = lightColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = DarkGreenLight,
    onPrimaryContainer = Color.White,

    secondary = AccentGreen,
    onSecondary = OnAccentGreen,
    secondaryContainer = AccentGreen.copy(alpha = 0.12f),
    onSecondaryContainer = DarkGreen,

    tertiary = AccentGold,
    onTertiary = InkText,
    tertiaryContainer = AccentGold.copy(alpha = 0.15f),
    onTertiaryContainer = InkText,

    background = SheetBackground,
    onBackground = InkText,

    surface = CardBackground,
    onSurface = InkText,
    surfaceVariant = SheetBackground,
    onSurfaceVariant = Dolphin,

    outline = Timberwolf,
    outlineVariant = QuickSilver,

    error = MaterialRed,
    onError = Color.White,
    errorContainer = ErrorContainerLight,
    onErrorContainer = MaterialRed
)

private val DarkColors = darkColorScheme(
    primary = AccentEmerald,
    onPrimary = ObsidianDark,
    primaryContainer = DarkGreenLight,
    onPrimaryContainer = MetallicSilver,

    secondary = AccentGreen,
    onSecondary = ObsidianDark,
    secondaryContainer = GaugeTrackDim,
    onSecondaryContainer = MetallicSilver,

    tertiary = AccentGold,
    onTertiary = ObsidianDark,
    tertiaryContainer = AccentGold.copy(alpha = 0.2f),
    onTertiaryContainer = AccentGold,

    background = SheetBackgroundDark,
    onBackground = InkTextDark,

    surface = CardBackgroundDark,
    onSurface = InkTextDark,
    surfaceVariant = DeepGunmetal,
    onSurfaceVariant = QuickSilver,

    outline = MutedSlate,
    outlineVariant = MutedSage,

    error = MaterialRed,
    onError = ObsidianDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = MaterialRed
)

@Composable
fun SalatiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}