package com.mhq.salati.shared.presentation.mapper

import com.mhq.salati.shared.domain.HijriMonth
import com.mhq.salati.shared.presentation.model.HijriMonthUi

fun HijriMonth.toUi(): HijriMonthUi = when (this) {
    HijriMonth.MUHARRAM -> HijriMonthUi.MUHARRAM
    HijriMonth.SAFAR -> HijriMonthUi.SAFAR
    HijriMonth.RABI_AL_AWWAL -> HijriMonthUi.RABI_AL_AWWAL
    HijriMonth.RABI_AL_THANI -> HijriMonthUi.RABI_AL_THANI
    HijriMonth.JUMADA_AL_AWWAL -> HijriMonthUi.JUMADA_AL_AWWAL
    HijriMonth.JUMADA_AL_THANI -> HijriMonthUi.JUMADA_AL_THANI
    HijriMonth.RAJAB -> HijriMonthUi.RAJAB
    HijriMonth.SHABAN -> HijriMonthUi.SHABAN
    HijriMonth.RAMADAN -> HijriMonthUi.RAMADAN
    HijriMonth.SHAWWAL -> HijriMonthUi.SHAWWAL
    HijriMonth.DHU_AL_QIDAH -> HijriMonthUi.DHU_AL_QIDAH
    HijriMonth.DHU_AL_HIJJAH -> HijriMonthUi.DHU_AL_HIJJAH
}