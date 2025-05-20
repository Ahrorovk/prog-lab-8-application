package com.ahrorovk.labwork.data.local.dataStore

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        val TOKEN_STATE = stringPreferencesKey("token_state")
        val LANGUAGE_STATE = intPreferencesKey("language_state")
    }
    private val prefs: SharedPreferences = context.getSharedPreferences("city_prefs", Context.MODE_PRIVATE)

    suspend fun updateTokenState(state: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_STATE] = state
        }
    }

    suspend fun updateLanguageState(state: Int) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_STATE] = state
        }
    }

    val getTokenState = context.dataStore.data.map {
        it[TOKEN_STATE] ?: ""
    }
    val getLanguageState = context.dataStore.data.map {
        it[LANGUAGE_STATE] ?: 0
    }
}