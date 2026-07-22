package com.mhq.salati.presentation.home.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.presentation.home.HomeContract
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun HomeContent(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> {
            HomeLoadingContent(modifier = modifier)
        }

        state.errorMessage != null -> {
            HomeErrorContent(
                errorMessage = state.errorMessage,
                locationPermissionState = state.locationPermission,
                onIntent = onIntent,
                modifier = modifier
            )
        }

        state.timings != null && state.date != null -> {
            HomeSuccessContent(
                prayerDate = state.date,
                prayerTimings = state.timings,
                mutedPrayers = state.mutedPrayers,
                onIntent = onIntent,
                modifier = modifier
            )
        }

        else -> {
            HomeAwaitingLocationPermissions()
        }
    }
}

@Preview(showBackground = true, name = "Home - Loading")
@Composable
private fun HomeContentLoadingPreview() {
    SalatiTheme {
        HomeContent(
            state = HomeContract.State(isLoading = true),
            onIntent = {},
        )
    }
}