package com.mhq.salati.shared.ui.errors

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.DarkGreen
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun LocationPermissionRequiredError(
    errorMessage: String,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.error, errorMessage),
            textAlign = TextAlign.Center,
            color = DarkGreen
        )
        Spacer(
            Modifier.height(8.dp)
        )
        Button(onClick = onRetryClicked) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun LocationPermissionRequiredErrorPreview() {
    SalatiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LocationPermissionRequiredError(
                errorMessage = "Location permission is required to calculate accurate prayer times.",
                onRetryClicked = {}
            )
        }
    }
}