package com.mhq.salati.presentation.home

import com.mhq.salati.domain.model.PrayerDate
import com.mhq.salati.domain.model.PrayerTimings

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val timings: PrayerTimings? = null,
        val date: PrayerDate? = null,
        val errorMessage: String? = null
    )

    sealed interface Intent {
        data object LoadPrayerTimes : Intent
        data object Retry : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
    }
}