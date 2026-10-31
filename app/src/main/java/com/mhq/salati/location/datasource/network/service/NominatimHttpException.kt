package com.mhq.salati.location.datasource.network.service

class NominatimHttpException(val statusCode: Int) : Exception("HTTP $statusCode")