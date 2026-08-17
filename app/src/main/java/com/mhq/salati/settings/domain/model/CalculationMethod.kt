package com.mhq.salati.settings.domain.model

import androidx.annotation.StringRes
import com.mhq.salati.R

enum class CalculationMethod(val apiMethodId: Int, @StringRes val displayNameRes: Int) {
    EGYPTIAN_GENERAL_AUTHORITY(5, R.string.calc_method_egyptian_general_authority),
    UMM_AL_QURA(4, R.string.calc_method_umm_al_qura),
    MUSLIM_WORLD_LEAGUE(3, R.string.calc_method_muslim_world_league),
    ISLAMIC_SOCIETY_OF_NORTH_AMERICA(2, R.string.calc_method_isna),
    UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI(1, R.string.calc_method_karachi);

    companion object {
        fun fromApiMethodId(id: Int): CalculationMethod =
            entries.firstOrNull { it.apiMethodId == id } ?: EGYPTIAN_GENERAL_AUTHORITY
    }
}