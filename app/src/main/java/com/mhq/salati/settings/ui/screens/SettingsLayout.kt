package com.mhq.salati.settings.ui.screens

import androidx.compose.ui.unit.dp

/** Wide screens (landscape, tablets, foldables, split-screen). */
internal object SettingsLayout {
    /** Tall screens: the settings list never stretches wider than this (big portrait tablets). */
    val SettingsPortraitMaxWidth = 560.dp

    /** A pane's rows never stretch wider than this, even when the pane itself is huge. */
    val PaneMaxWidth = 420.dp

    val PaneSpacing = 24.dp
}