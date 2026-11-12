package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.HijriMonth

/**
 * A date on the Hijri calendar, as shown next to the Gregorian one.
 *
 * @property day day of the Hijri month, 1-based
 * @property month the Hijri month
 * @property year the Hijri year
 */
data class HijriDate(
    val day: Int,
    val month: HijriMonth,
    val year: Int
)