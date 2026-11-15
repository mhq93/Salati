package com.mhq.salati.shared.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.components.SunRingLoader
import com.mhq.salati.shared.ui.theme.SalatiTheme

/**
 * A screen-sized loading state: the [indicator] with a caption under it. The caption cross-fades
 * when it changes, so a loader can announce each stage of the work.
 */
@Composable
fun LoadingContent(
    caption: String,
    modifier: Modifier = Modifier,
    indicator: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        indicator()
        Spacer(Modifier.height(24.dp))
        AnimatedContent(
            targetState = caption,
            transitionSpec = { fadeIn(tween(350)) togetherWith fadeOut(tween(350)) },
            label = "loadingCaption"
        ) { text ->
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoadingContentPreview() {
    SalatiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LoadingContent(caption = "Finding your place…") { SunRingLoader() }
        }
    }
}