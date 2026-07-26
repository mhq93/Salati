package com.mhq.salati.settings.domain.model

enum class CalculationMethod(val apiMethodId: Int, val displayName: String) {
    EGYPTIAN(5, "Egyptian General Authority"),
    MWL(3, "Muslim World League"),
    UMM_AL_QURA(4, "Umm Al-Qura, Makkah"),
    ISNA(2, "Islamic Society of North America"),
    KARACHI(1, "University of Islamic Sciences, Karachi");

    companion object {
        fun fromApiMethodId(id: Int): CalculationMethod =
            entries.firstOrNull { it.apiMethodId == id } ?: EGYPTIAN
    }
}