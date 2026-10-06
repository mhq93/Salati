package com.mhq.salati.location.datasource.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.locationDataStore: DataStore<Preferences> by preferencesDataStore(name = "location_prefs")

class LocationDataStore @Inject constructor(
    context: Context
) {
    private val dataStore = context.locationDataStore

    private object Keys {
        val LAT = doublePreferencesKey("latitude")
        val LON = doublePreferencesKey("longitude")
        val CITY = stringPreferencesKey("city_name")
        val COUNTRY = stringPreferencesKey("country_name")
    }

    val savedLocation: Flow<SavedLocation?> = dataStore.data.map { prefs ->
        val lat = prefs[Keys.LAT]
        val lon = prefs[Keys.LON]
        // A stored pair that isn't a valid coordinate counts as "no saved location" instead of crashing.
        val coordinates = if (lat != null && lon != null) {
            runCatching { Coordinates(lat, lon) }.getOrNull()
        } else null

        coordinates?.let {
            SavedLocation(
                coordinates = it,
                cityName = prefs[Keys.CITY],
                countryName = prefs[Keys.COUNTRY]
            )
        }
    }

    suspend fun save(location: SavedLocation) {
        dataStore.edit { prefs ->
            prefs[Keys.LAT] = location.coordinates.latitude
            prefs[Keys.LON] = location.coordinates.longitude
            if (location.cityName != null)
                prefs[Keys.CITY] = location.cityName
            else
                prefs.remove(Keys.CITY)
            if (location.countryName != null)
                prefs[Keys.COUNTRY] = location.countryName
            else
                prefs.remove(Keys.COUNTRY)
        }
    }
}