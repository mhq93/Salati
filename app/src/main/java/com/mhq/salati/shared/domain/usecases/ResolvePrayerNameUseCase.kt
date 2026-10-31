package com.mhq.salati.shared.domain.usecases

import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.PrayerNameResolver
import javax.inject.Inject

/** Gives the [PrayerName] a stored or Intent key stands for, or null for an unknown key. */
class ResolvePrayerNameUseCase @Inject constructor(
    private val prayerNameResolver: PrayerNameResolver
) {
    operator fun invoke(key: String): PrayerName? = prayerNameResolver.resolve(key)
}