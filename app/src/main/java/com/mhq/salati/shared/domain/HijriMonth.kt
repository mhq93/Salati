package com.mhq.salati.shared.domain

/**
 * The twelve months of the Hijri calendar, in order.
 *
 * @property number the 1-based month index used by Hijri date conversions and stored data
 */
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
        /**
         * Returns the month with the given [number], or [MUHARRAM] when no month matches.
         * The fallback keeps callers safe against out-of-range values
         * coming from external date converters or corrupted stored data.
         */
        fun fromNumber(number: Int): HijriMonth =
            entries.firstOrNull { it.number == number } ?: MUHARRAM
    }
}