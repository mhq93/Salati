package com.mhq.salati.settings.domain.model

import androidx.annotation.StringRes
import com.mhq.salati.R

enum class Madhab(val schoolId: Int, @StringRes val displayNameRes: Int) {
    SHAFI(0, R.string.madhab_shafi),
    HANAFI(1, R.string.madhab_hanafi);

    companion object {
        fun fromSchoolId(id: Int): Madhab =
            entries.firstOrNull { it.schoolId == id } ?: SHAFI
    }
}