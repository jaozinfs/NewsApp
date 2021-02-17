package com.example.common

import androidx.lifecycle.LiveData

interface TokenManager {
    val tokenObservable: LiveData<String?>

    fun checkAuthenticationByToken(token: String?): UserAuthentication
    fun getCurrentToken(): String?
}