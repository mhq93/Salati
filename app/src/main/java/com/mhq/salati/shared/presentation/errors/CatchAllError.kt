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
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.presentation.theme.DarkGreen

@Composable
fun CatchAllError(
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
            color = DarkGreen
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetryClicked) {
            Text(stringResource(R.string.retry))
        }
    }
}