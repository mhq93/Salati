package com.mhq.salati.location.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mhq.salati.location.domain.model.SavedLocation
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
        if (lat != null && lon != null) {
            SavedLocation(prefs[Keys.CITY], prefs[Keys.COUNTRY], lat, lon)
        } else null
    }

    suspend fun save(latitude: Double, longitude: Double, cityName: String?, countryName: String?) {
        dataStore.edit { prefs ->
            prefs[Keys.LAT] = latitude
            prefs[Keys.LON] = longitude
            if (cityName != null) prefs[Keys.CITY] = cityName else prefs.remove(Keys.CITY)
            if (countryName != null) prefs[Keys.COUNTRY] = countryName else prefs.remove(Keys.COUNTRY)
        }
    }
}