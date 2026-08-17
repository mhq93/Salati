package com.mhq.salati.settings.domain.model

import androidx.annotation.StringRes
import com.mhq.salati.R

enum class AdhanSound(@StringRes val displayNameRes: Int) {
    DEFAULT(R.string.adhan_sound_default),
    MAKKAH(R.string.adhan_sound_makkah),
    MADINAH(R.string.adhan_sound_madinah),
    SILENT(R.string.adhan_sound_silent)
}