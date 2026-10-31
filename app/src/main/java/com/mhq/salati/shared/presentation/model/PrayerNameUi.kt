package com.mhq.salati.shared.presentation.model

/**
 * A prayer or timing as the screens know it.
 * Mirrors the domain PrayerName; the mapper in shared/presentation/mapper converts between the two.
 */
enum class PrayerNameUi(val isMinorTiming: Boolean) {
    FAJR(false),
    DHUHR(false),
    ASR(false),
    MAGHRIB(false),
    ISHA(false),
    IMSAK(true),
    SHOROUQ(true),
    FIRST_THIRD(true),
    MIDNIGHT(true),
    LAST_THIRD(true);

    companion object {
        val majorEntries: List<PrayerNameUi> = entries.filter { !it.isMinorTiming }
    }
}