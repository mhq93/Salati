package com.mhq.salati.settings.domain.model

enum class CalculationMethod(val apiMethodId: Int, val displayName: String) {
    EGYPTIAN_GENERAL_AUTHORITY(5, "Egyptian General Authority"),
    UMM_AL_QURA(4, "Umm Al-Qura, Makkah"),
    MUSLIM_WORLD_LEAGUE(3, "Muslim World League"),
    ISLAMIC_SOCIETY_OF_NORTH_AMERICA(2, "Islamic Society of North America"),
    UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI(1, "University of Islamic Sciences, Karachi");

    companion object {
        fun fromApiMethodId(id: Int): CalculationMethod =
            entries.firstOrNull { it.apiMethodId == id } ?: EGYPTIAN_GENERAL_AUTHORITY
    }
}