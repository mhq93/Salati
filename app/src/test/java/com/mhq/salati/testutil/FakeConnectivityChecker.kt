package com.mhq.salati.testutil

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker

class FakeConnectivityChecker(private val connected: Boolean) : ConnectivityChecker {
    override fun isConnected() = connected
}