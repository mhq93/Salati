package com.mhq.salati.qibla.ui.components

import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.ScreenHorizontalPadding

/**
 * Geometry shared by the wide (landscape) Qibla screen and the calibration overlay.
 *
 * Both lay out two panes with the SAME outer padding, spacing and weights,
 * so each pane has the same width and position in both.
 * Content centered inside a pane therefore sits on the same vertical line in both:
 * the compass dial and the figure-8, and the Recalibrate and Done buttons.
 * Change a value here and both screens move together.
 */
internal object CompassWideLayout {
    val VerticalPadding = 16.dp
    val Spacing = 16.dp

    /** Start pane: compass dial / figure-8 animation. */
    const val DialPaneWeight = 1f

    /** End pane: status card / calibration text. */
    const val SidePaneWeight = 1f
}