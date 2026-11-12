package com.mhq.salati.prayertracker.ui.screens

import androidx.compose.ui.unit.dp

/** Screens (Portrait, landscape, tablets, split-screen). */
internal object TrackerLayout {
    /** Tall screens: content never stretches wider than this (big portrait tablets), centered. */
    val TrackerPortraitMaxWidth = 560.dp

    /** Calendar never gets wider than this inside its half of the screen. */
    val PaneContentMaxWidth = 340.dp

    /** Below this height the calendar stops shrinking and the pane scrolls instead. */
    val CalendarMinHeight = 216.dp

    /** Calendar height is capped at width x this, so cells don't become tall slivers on tablets. */
    const val CalendarMaxHeightPerWidth = 1.2f

    val PaneSpacing = 24.dp
}