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
            modifier = modifier
                .navigationBarsPadding()
                .padding(bottom = BottomNavDefaults.Height)
        ) {
            //            val showBanner = state.errorMessage == null
            //
            //            if (showBanner && !state.hasExactAlarmPermission) {
            //                SystemPermissionBanner(
            //                    message = stringResource(R.string.exact_alarms_are_disabled_prayer_alerts_may_not_ring_on_time),
            //                    onActionClick = { onIntent(HomeContract.Intent.ExactAlarmBannerClicked) },
            //                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            //                )
            //            }
            //
            //            if (showBanner && !state.hasNotificationPermission) {
            //                SystemPermissionBanner(
            //                    message = stringResource(R.string.notifications_are_disabled_you_won_t_see_prayer_reminders),
            //                    onActionClick = { onIntent(HomeContract.Intent.NotificationBannerClicked) },
            //                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            //                )
            //            }

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

//                else -> {
//                    AwaitingLocationPermissions(modifier = Modifier.weight(1f))
//                    //                    HomeContentError(
//                    //                        errorMessage = stringResource(R.string.location_permission_is_required_to_show_prayer_times),
//                    //                        locationPermissionState = state.locationPermission,
//                    //                        onIntent = onIntent,
//                    //                        modifier = Modifier.weight(1f)
//                    //                    )
//                }
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