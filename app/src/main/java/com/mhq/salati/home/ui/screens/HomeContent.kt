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
                        isLocationPermissionPermanentlyDenied = state.location.isPermanentlyDenied,
                        areLocationServicesDisabled = state.location.areServicesDisabled,
                        isLocationPermissionRequired = state.location.isPermissionRequired,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }
                state.prayerTimes.timings != null
                        && state.prayerTimes.date != null
                        && state.prayerTimes.prayerWindow != null -> {
                    HomeContentSuccess(
                        locationName = state.location.locationName,
                        prayerDate = state.prayerTimes.date,
                        prayerTimings = state.prayerTimes.timings,
                        nextPrayer = state.prayerTimes.prayerWindow.nextPrayer,
                        remaining = state.prayerTimes.remaining,
                        windowProgress = state.prayerTimes.windowProgress,
                        currentPrayerName = state.prayerTimes.currentPrayerName,
                        mutedPrayers = state.adhan.mutedPrayers,
                        pastPrayers = state.prayerTimes.pastPrayers,
                        isToday = state.dateBrowser.isBrowsingToday,
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