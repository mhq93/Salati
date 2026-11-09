package com.mhq.salati.shared.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Single-line text that shrinks its font (down to [minFontSize]) until it fits the width it is given.
 *
 * Use it wherever a label must stay on ONE line, so a longer translation (Arabic day / prayer names)
 * or a narrow pane (landscape, split-screen) shrinks the text instead of wrapping and growing the
 * parent vertically. The text stays hidden until the final size is found, so there is no visible jump.
 *
 * The shrink restarts whenever the text, font size, screen width or font scale changes.
 */
@Composable
fun ShrinkToFitText(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    minFontSize: TextUnit = 9.sp,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null
) {
    val configuration = LocalConfiguration.current
    var sizeSp by remember(text, fontSize, configuration.screenWidthDp, configuration.fontScale) {
        mutableFloatStateOf(fontSize.value)
    }
    var fits by remember(text, fontSize, configuration.screenWidthDp, configuration.fontScale) {
        mutableStateOf(false)
    }

    Text(
        text = text,
        color = color,
        fontSize = sizeSp.sp,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        onTextLayout = { result ->
            if (result.didOverflowWidth && sizeSp > minFontSize.value) {
                sizeSp = (sizeSp * 0.92f).coerceAtLeast(minFontSize.value)
            } else {
                fits = true
            }
        },
        modifier = modifier.drawWithContent { if (fits) drawContent() }
    )
}