package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import javax.inject.Inject

class ToggleMutePrayerUseCase @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository
) {
    suspend operator fun invoke(date: String, prayerName: String, muted: Boolean) {
        mutedPrayersRepository.toggleMute(date, prayerName, muted)
    }
}