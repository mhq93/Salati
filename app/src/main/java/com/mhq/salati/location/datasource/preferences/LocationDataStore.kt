package com.mhq.salati.location.datasource.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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

    val storedLocation: Flow<StoredLocation?> = dataStore.data.map { prefs ->
        val lat = prefs[Keys.LAT]
        val lon = prefs[Keys.LON]
        if (lat != null && lon != null) {
            StoredLocation(
                latitude = lat,
                longitude = lon,
                cityName = prefs[Keys.CITY],
                countryName = prefs[Keys.COUNTRY]
            )
        } else null
    }

    suspend fun save(location: StoredLocation) {
        dataStore.edit { prefs ->
            prefs[Keys.LAT] = location.latitude
            prefs[Keys.LON] = location.longitude
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