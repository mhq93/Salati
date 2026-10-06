package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.adhan.domain.usecases.ObserveMutedPrayersUseCase
import com.mhq.salati.adhan.domain.usecases.ToggleMutePrayerUseCase
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/** Which prayers have their adhan muted on a given day. */
class MutedPrayersHandler @Inject constructor(
    private val observeMutedPrayersUseCase: ObserveMutedPrayersUseCase,
    private val toggleMutePrayerUseCase: ToggleMutePrayerUseCase
) {
    fun observe(date: LocalDate): Flow<Set<PrayerName>> = observeMutedPrayersUseCase(date)

    suspend fun toggle(date: LocalDate, prayerName: PrayerName) {
        toggleMutePrayerUseCase(date, prayerName)
    }
}