package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class ObserveMutedPrayersUseCase @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    /** The prayers muted on [date]. Rows for days that already passed are cleared first. */
    operator fun invoke(date: LocalDate): Flow<Set<PrayerName>> = flow {
        mutedPrayersRepository.purgePastDates()
        emitAll(mutedPrayersRepository.observeMutedPrayers(date.format(dateKeyFormatter)))
    }
}