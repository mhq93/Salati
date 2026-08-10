package com.mhq.salati.shared

import android.app.Application
import com.mhq.salati.adhan.data.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SalatiApplication : Application() {
    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        notificationHelper.ensureChannelsCreated()
    }
}