package com.mhq.salati.settings.domain.model

import androidx.annotation.StringRes
import com.mhq.salati.R

enum class ThemeMode(@StringRes val displayNameRes: Int) {
    SYSTEM(R.string.theme_system_default),
    LIGHT(R.string.theme_light),
    DARK(R.string.theme_dark)
}