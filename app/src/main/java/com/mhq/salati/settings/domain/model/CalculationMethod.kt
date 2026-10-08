package com.mhq.salati.settings.domain.model

enum class CalculationMethod(val apiMethodId: Int) {
    UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI(1),
    ISLAMIC_SOCIETY_OF_NORTH_AMERICA(2),
    MUSLIM_WORLD_LEAGUE(3),
    UMM_AL_QURA(4),
    EGYPTIAN_GENERAL_AUTHORITY(5);

    companion object {
        fun fromApiMethodId(id: Int): CalculationMethod =
            entries.firstOrNull { it.apiMethodId == id } ?: EGYPTIAN_GENERAL_AUTHORITY
    }
}