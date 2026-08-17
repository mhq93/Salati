package com.mhq.salati.settings.domain.model

import androidx.annotation.StringRes
import com.mhq.salati.R

enum class AppLanguage(val code: String, @StringRes val displayNameRes: Int) {
    ENGLISH("en", R.string.language_english),
    ARABIC("ar", R.string.language_arabic)
}