package com.mhq.salati.shared.ui

import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.model.HijriMonthUi

val HijriMonthUi.labelRes: Int
    @StringRes get() = when (this) {
        HijriMonthUi.MUHARRAM -> R.string.hijri_muharram
        HijriMonthUi.SAFAR -> R.string.hijri_safar
        HijriMonthUi.RABI_AL_AWWAL -> R.string.hijri_rabi_al_awwal
        HijriMonthUi.RABI_AL_THANI -> R.string.hijri_rabi_al_akhar
        HijriMonthUi.JUMADA_AL_AWWAL -> R.string.hijri_jumada_al_oula
        HijriMonthUi.JUMADA_AL_THANI -> R.string.hijri_jumada_al_akhera
        HijriMonthUi.RAJAB -> R.string.hijri_rajab
        HijriMonthUi.SHABAN -> R.string.hijri_shaban
        HijriMonthUi.RAMADAN -> R.string.hijri_ramadan
        HijriMonthUi.SHAWWAL -> R.string.hijri_shawwal
        HijriMonthUi.DHU_AL_QIDAH -> R.string.hijri_dhu_al_qidah
        HijriMonthUi.DHU_AL_HIJJAH -> R.string.hijri_dhu_al_hijjah
    }