package com.mhq.salati.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeContent(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.errorMessage != null -> {
                Text(text = "Error: ${state.errorMessage}")
                when {
                    state.locationPermission.permanentlyDenied -> {
                        Button(
                            onClick = {
                                onIntent(
                                    HomeContract.Intent.AccessAppSettings
                                )
                            }
                        ) {
                            Text("Open Settings")
                        }
                    }

                    state.locationPermission.servicesDisabled -> {
                        Button(onClick = {
                            onIntent(
                                HomeContract.Intent.AccessDeviceLocationSettings
                            )
                        }
                        ) {
                            Text("Enable Location")
                        }
                    }

                    else -> {
                        Button(
                            onClick = {
                                onIntent(HomeContract.Intent.Retry)
                            }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }

            state.timings != null && state.date != null -> {
                Text(text = state.date.readable)
                Text(text = "Fajr: ${state.timings.fajr}")
                Text(text = "Sunrise: ${state.timings.sunrise}")
                Text(text = "Dhuhr: ${state.timings.dhuhr}")
                Text(text = "Asr: ${state.timings.asr}")
                Text(text = "Maghrib: ${state.timings.maghrib}")
                Text(text = "Isha: ${state.timings.isha}")
            }

            else -> {
                Text(text = "Waiting for location permission…")
            }
        }
    }
}