package com.mhq.salati.onboarding.datasource.preferences

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

private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_prefs")

@Singleton
class OnboardingDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.onboardingDataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun hasCompletedOnboarding(): Boolean {
        return context.onboardingDataStore.data.first()[ONBOARDING_COMPLETED] ?: false
    }
}