package com.mhq.salati.presentation.common.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun LoadingContentPreview() {
    SalatiTheme() {
        LoadingContent()
    }
}