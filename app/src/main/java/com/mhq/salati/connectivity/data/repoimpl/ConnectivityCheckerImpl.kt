package com.mhq.salati.connectivity.data.repoimpl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConnectivityCheckerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectivityChecker {

    override fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}