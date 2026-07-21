package com.mhq.salati.data.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.domain.repo.alarms.MutedPrayersRepository
import com.mhq.salati.domain.repo.prayers.PrayerTimesRepository
import com.mhq.salati.domain.usecases.alarms.ScheduleDailyPrayerAlarmsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var prayerTimesRepository: PrayerTimesRepository

    @Inject
    lateinit var mutedPrayersRepository: MutedPrayersRepository

    @Inject
    lateinit var scheduleDailyPrayerAlarmsUseCase: ScheduleDailyPrayerAlarmsUseCase

    @Inject
    lateinit var locationProvider: LocationProvider

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val today = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
                val (latitude, longitude) = locationProvider.getCurrentLocation()

                val cached = prayerTimesRepository.getCachedTimings(today, latitude, longitude)
                val mutedPrayers = mutedPrayersRepository.getMutedPrayers()

                if (cached != null) {
                    scheduleDailyPrayerAlarmsUseCase(cached.timings, today, mutedPrayers)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}