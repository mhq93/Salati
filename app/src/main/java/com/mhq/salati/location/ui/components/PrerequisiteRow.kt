package com.mhq.salati.location.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.mhq.salati.shared.ui.components.ActionRow
import com.mhq.salati.shared.ui.components.GroupDivider

/** One missing requirement; it animates away as soon as it is fixed. */
@Composable
fun PrerequisiteRow(
    isMissing: Boolean,
    showDivider: Boolean,
    icon: ImageVector,
    title: String,
    actionLabel: String,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isMissing,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column {
            ActionRow(
                icon = icon,
                title = title,
                valueLabel = actionLabel,
                onClick = onClick
            )
            if (showDivider) GroupDivider()
        }
    }
}