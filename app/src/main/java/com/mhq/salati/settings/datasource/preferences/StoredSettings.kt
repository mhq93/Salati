package com.mhq.salati.settings.datasource.preferences

/** The settings exactly as the DataStore keeps them. Choices are stored as text; null means never saved. */
data class StoredSettings(
    val notificationsEnabled: Boolean,
    val calculationMethod: String?,
    val madhab: String?,
    val themeMode: String?,
    val language: String?,
    val adhanSound: String?,
    val customAlarmSound: String?,
    val hijriDateOffset: Int,
    val timeFormat: String?
)