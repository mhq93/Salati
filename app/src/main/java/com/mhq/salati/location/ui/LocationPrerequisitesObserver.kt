package com.mhq.salati.location.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.mhq.salati.connectivity.ui.components.rememberNetworkConnected
import com.mhq.salati.permissions.ui.rememberLocationServicesEnabled

/** Reports the two things the blocked screen waits for that change outside the app. */
@Composable
fun LocationPrerequisitesObserver(
    onServicesChanged: (enabled: Boolean) -> Unit,
    onConnectivityChanged: (connected: Boolean) -> Unit
) {
    val servicesEnabled by rememberLocationServicesEnabled()
    val isConnected by rememberNetworkConnected()

    LaunchedEffect(servicesEnabled) { onServicesChanged(servicesEnabled) }
    LaunchedEffect(isConnected) { onConnectivityChanged(isConnected) }
}