package com.example.newsapp.userpreferences

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userTokenFlow: Flow<UserTokenStore?>
    suspend fun saveToken(token: String)
    suspend fun removeToken()
}