package com.mhq.salati.shared.domain.usecases

import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.PrayerNameResolver
import javax.inject.Inject

/**
 * Gives the [PrayerName] a stored or Intent key stands for, or null for an unknown key.
 *
 * Thin pass-through over [PrayerNameResolver]; exists so presentation code can resolve keys
 * through the standard use-case seam without reaching into Data.
 */
class ResolvePrayerNameUseCase @Inject constructor(
    private val prayerNameResolver: PrayerNameResolver
) {
    /**
     * @param key the string key carried by an alarm Intent or a saved row
     * @return the matching prayer, or null when the key is unknown or stale
     */
    operator fun invoke(key: String): PrayerName? = prayerNameResolver.resolve(key)
}