package com.mhq.salati.shared.domain

enum class HijriMonth(val number: Int) {
    MUHARRAM(1),
    SAFAR(2),
    RABI_AL_AWWAL(3),
    RABI_AL_THANI(4),
    JUMADA_AL_AWWAL(5),
    JUMADA_AL_THANI(6),
    RAJAB(7),
    SHABAN(8),
    RAMADAN(9),
    SHAWWAL(10),
    DHU_AL_QIDAH(11),
    DHU_AL_HIJJAH(12);

    companion object {
        fun fromNumber(number: Int): HijriMonth =
            entries.firstOrNull { it.number == number } ?: MUHARRAM
    }
}