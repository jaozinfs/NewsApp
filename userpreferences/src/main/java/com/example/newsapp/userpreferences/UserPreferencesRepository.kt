package com.example.newsapp.userpreferences

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userToken: Flow<UserTokenStore?>
    suspend fun saveToken(token: String)
}