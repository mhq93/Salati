package com.mhq.salati.shared.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.isWideLayout
import com.mhq.salati.shared.ui.screenHorizontalMargin
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.tabHeaderGradient

/**
 * Header shared by the tab screens.
 *
 * The height is a MINIMUM ([heightIn]), never a fixed value,
 * so a larger font scale or Arabic line heights grow the header instead of clipping it.
 * The gradient runs edge to edge (behind the status bar and any system bars);
 * only the content is pushed clear of them.
 */
@Composable
fun TabHeader(
    icon: ImageVector,
    title: String,
    subtitle: String,
    trailingContent: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isWide = isWideLayout()

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tabHeaderGradient)
            .statusBarsPadding()
            .windowInsetsPadding(
                WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal)
            )
            .heightIn(min = if (isWide) 64.dp else 112.dp)
            .padding(vertical = if (isWide) 8.dp else 24.dp, horizontal = screenHorizontalMargin())
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(if (isWide) 40.dp else 56.dp)
                        .clip(RoundedCornerShape(if (isWide) 10.dp else 16.dp))
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f)
                        ),
                ) {
                    Icon(
                        imageVector = icon,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null,
                        modifier = Modifier.size(if (isWide) 20.dp else 28.dp)
                    )
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = if (isWide) 20.sp else 26.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            lineHeight = if (isWide) 24.sp else 32.sp,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    )
                    if (!isWide) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = subtitle,
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.85f),
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                lineHeight = 18.sp,
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None
                                )
                            )
                        )
                    }
                }
            }
            trailingContent()
        }
    }
}

@Preview
@Composable
private fun TabHeaderPreview() {
    SalatiTheme {
        TabHeader(
            icon = Icons.Default.Explore,
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}