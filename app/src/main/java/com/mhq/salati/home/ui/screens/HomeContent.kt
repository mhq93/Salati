package com.mhq.salati.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.ui.navigation.BottomNavBarDefaults
import com.mhq.salati.shared.ui.asString
import com.mhq.salati.shared.ui.screens.LoadingContent
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun HomeContent(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = BottomNavBarDefaults.Height)
        ) {
            when {
                state.isLoading -> {
                    LoadingContent(modifier = Modifier.weight(1f))
                }
                state.errorMessage != null -> {
                    HomeContentError(
                        errorMessage = state.errorMessage.asString(),
                        isLocationPermissionPermanentlyDenied = state.locationUiState.isPermanentlyDenied,
                        areLocationServicesDisabled = state.locationUiState.areServicesDisabled,
                        isLocationPermissionRequired = state.locationUiState.isPermissionRequired,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }
                state.prayerTimesUiState.timings != null
                        && state.prayerTimesUiState.date != null
                        && state.prayerTimesUiState.nextPrayerName != null -> {
                    HomeContentSuccess(
                        locationName = state.locationUiState.locationName,
                        prayerDate = state.prayerTimesUiState.date,
                        prayerTimings = state.prayerTimesUiState.timings,
                        nextPrayer = state.prayerTimesUiState.nextPrayerName,
                        remaining = state.prayerTimesUiState.remaining,
                        windowProgress = state.prayerTimesUiState.windowProgress,
                        currentPrayerName = state.prayerTimesUiState.currentPrayerName,
                        mutedPrayers = state.adhanUiState.mutedPrayers,
                        pastPrayers = state.prayerTimesUiState.pastPrayers,
                        isToday = state.dateBrowserUiState.isBrowsingToday,
                        timeFormat = state.timeFormat,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
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