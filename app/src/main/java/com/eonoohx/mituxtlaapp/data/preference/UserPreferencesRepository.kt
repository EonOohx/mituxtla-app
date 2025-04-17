package com.eonoohx.mituxtlaapp.data.preference

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

enum class AppTheme {
    LIGHT, DARK
}

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    val selectedTheme: Flow<AppTheme> =
        dataStore.data.catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else throw it
        }.map { preferences -> if (preferences[THEME] == "DARK") AppTheme.DARK else AppTheme.LIGHT }

    suspend fun saveThemePreference(theme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[THEME] = theme.name
        }
    }

    private companion object {
        val THEME = stringPreferencesKey("theme")
        const val TAG = "UserPreferencesRepo"
    }
}