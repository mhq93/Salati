package com.mhq.salati.settings.domain.model

enum class Madhab(val schoolId: Int) {
    SHAFI(0),
    HANAFI(1);

    companion object {
        fun fromSchoolId(id: Int): Madhab =
            entries.firstOrNull { it.schoolId == id } ?: SHAFI
    }
}