package com.mhq.salati.settings.domain.model

enum class Madhab(val schoolId: Int, val displayName: String) {
    SHAFI(0, "Shafi'i (Standard)"),
    HANAFI(1, "Hanafi");

    companion object {
        fun fromSchoolId(id: Int): Madhab =
            entries.firstOrNull { it.schoolId == id } ?: SHAFI
    }
}