package com.mhq.salati.home.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.ui.errors.CatchAllError
import com.mhq.salati.shared.ui.errors.NoConnectionError
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun HomeContentError(
    errorMessage: String,
    isNoConnection: Boolean,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isNoConnection -> {
            NoConnectionError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(HomeContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }

        else -> {
            CatchAllError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(HomeContract.Intent.RetryRequested) },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentErrorPreview() {
    SalatiTheme()  {
        HomeContentError(
            errorMessage = "Some message",
            isNoConnection = false,
            onIntent = {}
        )
    }
}