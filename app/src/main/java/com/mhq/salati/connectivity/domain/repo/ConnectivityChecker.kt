package com.mhq.salati.connectivity.domain.repo

interface ConnectivityChecker {
    fun isConnected(): Boolean
}