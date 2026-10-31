package com.mhq.salati.home.presentation.model

import com.mhq.salati.shared.presentation.model.HijriMonthUi

data class HijriDateUi(
    val day: Int,
    val month: HijriMonthUi,
    val year: Int
)