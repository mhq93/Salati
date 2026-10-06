package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.HijriMonth

data class HijriDate(
    val day: Int,
    val month: HijriMonth,
    val year: Int
)
