package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toDateKey
import java.time.LocalDate
import javax.inject.Inject

class ToggleMutePrayerUseCase @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository
) {
    /** Flips the mute state of [prayerName] on [date],
     * based on what is stored (not on what the screen last showed). */
    suspend operator fun invoke(date: LocalDate, prayerName: PrayerName) {
        val dateKey = date.toDateKey()
        val currentlyMuted = prayerName in mutedPrayersRepository.getMutedPrayers(dateKey)
        mutedPrayersRepository.toggleMute(dateKey, prayerName, !currentlyMuted)
    }
}