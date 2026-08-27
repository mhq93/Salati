package com.mhq.salati.shared.presentation.errors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun LocationPermissionPermanentlyDeniedError(
    errorMessage: String,
    onOpenSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        val formattedErrorMessage =
            stringResource(R.string.error, errorMessage)
                .replace(". ", ".\n")
        Text(
            text = formattedErrorMessage,
            color = DarkGreen,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onOpenSettingsClicked) {
            Text(stringResource(R.string.open_settings))
        }
    }
}

@Preview
@Composable
private fun LocationPermissionPermanentlyDeniedErrorPreview() {
    SalatiTheme() {
        LocationPermissionPermanentlyDeniedError(
            errorMessage = "Some peculiar error message",
            onOpenSettingsClicked = {}
        )
    }
}