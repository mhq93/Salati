package com.mhq.salati.shared.ui.theme

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
    primary = RadiantEmerald,
    onPrimary = CelestialObsidian,
    primaryContainer = DeepSeaTeal,
    onPrimaryContainer = CosmicWhite,

    secondary = EmeraldGlow,
    onSecondary = CelestialObsidian,
    secondaryContainer = AuroraMuted,
    onSecondaryContainer = AstralSlate,

    tertiary = CelestialGold,
    onTertiary = CelestialObsidian,
    tertiaryContainer = AuroraMuted,
    onTertiaryContainer = CelestialGold,

    background = CelestialObsidian,
    onBackground = CosmicWhite,

    surface = CosmicGunmetal,
    onSurface = CosmicWhite,
    surfaceVariant = TwilightSurface,
    onSurfaceVariant = AstralSlate,

    outline = CosmicMuted,
    outlineVariant = CelestialGoldMuted.copy(alpha = 0.4f),

    error = ErrorCrimsonDark,
    onError = CelestialObsidian,
    errorContainer = ErrorCrimsonContainer,
    onErrorContainer = ErrorCrimsonDark
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
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}