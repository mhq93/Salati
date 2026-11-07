package com.mhq.salati.permissions.datasource.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.locationPermissionPrefs: DataStore<Preferences> by preferencesDataStore(name = "location_permission_prefs")

@Singleton
class LocationPermissionDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val requestedKey = booleanPreferencesKey("location_permission_requested")

    suspend fun wasRequested(): Boolean =
        context.locationPermissionPrefs.data.first()[requestedKey] ?: false

    suspend fun markRequested() {
        context.locationPermissionPrefs.edit { it[requestedKey] = true }
    }
}