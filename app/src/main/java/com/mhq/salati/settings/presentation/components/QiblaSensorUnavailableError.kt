package com.mhq.salati.settings.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.theme.DarkGreen

@Composable
fun QiblaSensorUnavailableError(
    errorMessage: String,
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
        Spacer(
            Modifier.height(8.dp)
        )
        Text(
            text = stringResource(R.string.this_device_doesn_t_have_the_sensors_needed_for_a_compass),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(
            Modifier.height(4.dp)
        )
        Text(
            text = stringResource(R.string.qibla_direction_can_t_be_shown_here),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}