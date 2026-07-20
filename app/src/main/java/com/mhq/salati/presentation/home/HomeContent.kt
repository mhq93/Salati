package com.mhq.salati.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun HomeContent(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Error: ${state.errorMessage}"
                )
                when {
                    state.locationPermission.permanentlyDenied -> {
                        Button(onClick = {
                            onIntent(HomeContract.Intent.AccessAppSettings)
                        }) {
                            Text("Open Settings")
                        }
                    }

                    state.locationPermission.servicesDisabled -> {
                        Button(onClick = {
                            onIntent(HomeContract.Intent.AccessDeviceLocationSettings)
                        }) {
                            Text("Enable Location")
                        }
                    }

                    else -> {
                        Button(onClick = {
                            onIntent(HomeContract.Intent.Retry)
                        }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }

        state.timings != null && state.date != null -> {
            HomeSuccessContent(
                prayerDate = state.date,
                prayerTimings = state.timings,
                modifier = modifier
            )
        }

        else -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                Text(text = "Waiting for location permission…")
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
            onIntent = {}
        )
    }
}