package com.mhq.salati.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

//@Composable
//fun rememberNetworkConnected(): State<Boolean> {
//    val context = LocalContext.current
//
//    val connectivityManager = remember(context) {
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    }
//
//    fun isCurrentlyConnected(): Boolean {
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//    }
//
//    return produceState(initialValue = isCurrentlyConnected(), connectivityManager) {
//        val callback = object : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network) {
//                value = isCurrentlyConnected()
//            }
//
//            override fun onLost(network: Network) {
//                value = isCurrentlyConnected()
//            }
//
//            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
//                value = isCurrentlyConnected()
//            }
//        }
//
//        val request = NetworkRequest.Builder()
//            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//            .build()
//
//        connectivityManager.registerNetworkCallback(request, callback)
//
//        // FIX: Change awaitClose to awaitDispose
//        awaitDispose {
//            connectivityManager.unregisterNetworkCallback(callback)
//        }
//    }
//}


@Composable
fun rememberNetworkConnected(): State<Boolean> {
    val context = LocalContext.current
    val connectivityManager = remember {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    val connected = remember {
        mutableStateOf(
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        )
    }

    DisposableEffect(Unit) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                connected.value = true
            }

            override fun onLost(network: Network) {
                connected.value = false
            }

            override fun onCapabilitiesChanged(
                network: Network,
                capabilities: NetworkCapabilities
            ) {
                connected.value =
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, callback)
        onDispose { connectivityManager.unregisterNetworkCallback(callback) }
    }

    return connected
}