package com.mhq.salati.shared.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * Longest a value label may get before it shrinks and then clips. Without a cap, a long Arabic
 * translation (e.g. calculation method names) is measured with the full row width and wraps,
 * growing the row vertically and squeezing the weighted title into a sliver.
 */
private val ValueLabelMaxWidth = 200.dp

/**
 * Settings-style row with a trailing value label and a chevron.
 *
 * The value label stays on ONE line: it shrinks (via [ShrinkToFitText]), then clips, but never
 * wraps, so a longer translation or a narrow pane (landscape, split-screen) cannot grow this row
 * vertically. Titles may still wrap inside their weighted space, which is bounded.
 */
@Composable
fun ActionRow(
    icon: ImageVector,
    title: String,
    valueLabel: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        IconBadge(icon, isEnabled)
        Spacer(Modifier.width(14.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color =
                if (isEnabled)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.weight(1f)
        )

        ShrinkToFitText(
            text = valueLabel,
            fontSize = 13.sp,
            color =
                if (isEnabled)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.widthIn(max = ValueLabelMaxWidth)
        )

        Spacer(Modifier.width(4.dp))

        Icon(
            imageVector = if (isRtl) Icons.Filled.ChevronLeft else Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ActionRowPreview() {
    SalatiTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            ActionRow(
                icon = Icons.Default.Language,
                title = "App Language",
                valueLabel = "English",
                onClick = {}
            )
        }
    }
}

@Preview(name = "Long Arabic Value Label", locale = "ar", showBackground = true)
@Composable
private fun ActionRowLongArabicValuePreview() {
    SalatiTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            ActionRow(
                icon = Icons.Default.Language,
                title = "طريقة الحساب",
                valueLabel = "الهيئة المصرية العامة للمساحة",
                onClick = {}
            )
        }
    }
}