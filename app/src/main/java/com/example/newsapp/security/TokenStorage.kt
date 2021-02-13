package com.example.newsapp.security

import androidx.lifecycle.LiveData

sealed class UserTokenState {
    data class UserAuthenticated(val token: String) : UserTokenState()
    object UserNotAuthenticated : UserTokenState()
}

interface TokenStorage {

    fun setToken(token: String)

    fun deleteToken(): Boolean
}

