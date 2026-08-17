package com.mhq.salati

import androidx.annotation.StringRes

enum class HijriMonth(val number: Int, @StringRes val labelRes: Int) {
    MUHARRAM(1, R.string.hijri_muharram),
    SAFAR(2, R.string.hijri_safar),
    RABI_AL_AWWAL(3, R.string.hijri_rabi_al_awwal),
    RABI_AL_THANI(4, R.string.hijri_rabi_al_akhar),
    JUMADA_AL_AWWAL(5, R.string.hijri_jumada_al_oula),
    JUMADA_AL_THANI(6, R.string.hijri_jumada_al_akhera),
    RAJAB(7, R.string.hijri_rajab),
    SHABAN(8, R.string.hijri_shaban),
    RAMADAN(9, R.string.hijri_ramadan),
    SHAWWAL(10, R.string.hijri_shawwal),
    DHU_AL_QIDAH(11, R.string.hijri_dhu_al_qidah),
    DHU_AL_HIJJAH(12, R.string.hijri_dhu_al_hijjah);

    companion object {
        fun fromNumber(number: Int): HijriMonth =
            entries.firstOrNull { it.number == number } ?: MUHARRAM
    }
}