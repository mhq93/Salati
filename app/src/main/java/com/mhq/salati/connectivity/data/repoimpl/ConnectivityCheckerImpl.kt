package com.mhq.salati.connectivity.data.repoimpl

import com.mhq.salati.connectivity.datasource.device.ConnectivityDataSource
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import javax.inject.Inject

class ConnectivityCheckerImpl @Inject constructor(
    private val connectivityDataSource: ConnectivityDataSource
) : ConnectivityChecker {

    override fun isConnected(): Boolean = connectivityDataSource.isConnected()
}