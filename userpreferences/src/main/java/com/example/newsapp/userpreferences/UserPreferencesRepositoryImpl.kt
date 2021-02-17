package com.example.newsapp.userpreferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class UserPreferencesRepositoryImpl
    : UserPreferencesRepository, KoinComponent {
    private val USER_PREFS_DATASTORE_NAME = "news_app_user_prefs"


    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    private val dataStore: DataStore<Preferences> by inject {
        parametersOf(USER_PREFS_DATASTORE_NAME)
    }

    override val userTokenFlow: Flow<UserTokenStore>
        get() = dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            val token = preferences[PreferencesKeys.USER_TOKEN].takeIf {
                it != ""
            }
            UserTokenStore(token)
        }


    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[PreferencesKeys.USER_TOKEN] = token
        }
    }

    override suspend fun removeToken() {
        dataStore.edit {
            it[PreferencesKeys.USER_TOKEN] = ""
        }
    }


}