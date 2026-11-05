package com.mhq.salati.qibla.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.ui.errors.QiblaSensorUnavailableError
import com.mhq.salati.shared.ui.errors.CatchAllError
import com.mhq.salati.shared.ui.errors.NoConnectionError
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun QiblaContentError(
    errorMessage: String,
    isNoConnection: Boolean,
    sensorUnavailable: Boolean,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isNoConnection -> {
            NoConnectionError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(QiblaContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }

        sensorUnavailable -> {
            QiblaSensorUnavailableError(
                errorMessage = errorMessage,
                modifier = modifier
            )
        }

        else -> {
            CatchAllError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(QiblaContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun QiblaContentErrorPreview() {
    SalatiTheme()  {
        QiblaContentError(
            errorMessage = "Some message",
            isNoConnection = false,
            sensorUnavailable = false,
            onIntent = {}
        )
    }
}