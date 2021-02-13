package com.example.newsapp.userpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryRepositoryImpl(
    private val context: Context
) : UserPreferencesRepository {
    private val USER_PREFS_DATASTORE_NAME = "news_app_user_prefs"

    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    private val dataStore: DataStore<Preferences> =
        context.createDataStore(
            name = USER_PREFS_DATASTORE_NAME
        )

    override val userToken: Flow<UserTokenStore>
        get() = dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            UserTokenStore(preferences[PreferencesKeys.USER_TOKEN])
        }

    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[PreferencesKeys.USER_TOKEN] = token
        }
    }

}