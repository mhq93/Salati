package com.mhq.salati.shared.ui

import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.shared.domain.HijriMonth

// The user-facing name of a Hijri month. Lives here (not in the domain enum) because it is an Android resource.
val HijriMonth.labelRes: Int
    @StringRes get() = when (this) {
        HijriMonth.MUHARRAM -> R.string.hijri_muharram
        HijriMonth.SAFAR -> R.string.hijri_safar
        HijriMonth.RABI_AL_AWWAL -> R.string.hijri_rabi_al_awwal
        HijriMonth.RABI_AL_THANI -> R.string.hijri_rabi_al_akhar
        HijriMonth.JUMADA_AL_AWWAL -> R.string.hijri_jumada_al_oula
        HijriMonth.JUMADA_AL_THANI -> R.string.hijri_jumada_al_akhera
        HijriMonth.RAJAB -> R.string.hijri_rajab
        HijriMonth.SHABAN -> R.string.hijri_shaban
        HijriMonth.RAMADAN -> R.string.hijri_ramadan
        HijriMonth.SHAWWAL -> R.string.hijri_shawwal
        HijriMonth.DHU_AL_QIDAH -> R.string.hijri_dhu_al_qidah
        HijriMonth.DHU_AL_HIJJAH -> R.string.hijri_dhu_al_hijjah
    }