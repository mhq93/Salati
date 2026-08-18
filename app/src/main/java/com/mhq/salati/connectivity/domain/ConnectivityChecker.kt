package com.mhq.salati.connectivity.domain

interface ConnectivityChecker {
    fun isConnected(): Boolean
}