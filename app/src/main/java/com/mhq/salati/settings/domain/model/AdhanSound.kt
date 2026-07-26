package com.mhq.salati.settings.domain.model

enum class AdhanSound(val displayName: String) {
    DEFAULT("Default Beep"),
    MAKKAH("Makkah Adhan"),
    MADINAH("Madinah Adhan"),
    SILENT("Silent (Notification Only)")
}