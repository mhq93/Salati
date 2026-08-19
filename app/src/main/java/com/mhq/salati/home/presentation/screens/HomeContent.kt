package com.mhq.salati.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.presentation.components.BottomNavDefaults
import com.mhq.salati.shared.presentation.components.asString
import com.mhq.salati.shared.presentation.screens.LoadingContent
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import com.mhq.salati.shared.presentation.theme.SheetBackground

@Composable
fun HomeContent(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SheetBackground)
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = BottomNavDefaults.Height)
        ) {
            when {
                state.isLoading -> {
                    LoadingContent(modifier = Modifier.weight(1f))
                }

                state.errorMessage != null -> {
                    HomeContentError(
                        errorMessage = state.errorMessage.asString(),
                        locationPermissionState = state.locationPermission,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f)
                    )
                }

                state.timings != null && state.date != null && state.nextPrayerInfo != null -> {
                    HomeContentSuccess(
                        locationName = state.locationName,
                        prayerDate = state.date,
                        prayerTimings = state.timings,
                        remainingMillis = state.remainingMillis,
                        nextPrayerInfo = state.nextPrayerInfo,
                        currentPrayerName = state.currentPrayerName,
                        mutedPrayers = state.mutedPrayers,
                        pastPrayers = state.pastPrayers,
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