package com.mhq.salati.domain.usecases.alarms

import com.mhq.salati.domain.repo.alarms.MutedPrayersRepository
import javax.inject.Inject

class ToggleMutePrayerUseCase @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository
) {
    suspend operator fun invoke(prayerName: String, muted: Boolean) {
        mutedPrayersRepository.toggleMute(prayerName, muted)
    }
}