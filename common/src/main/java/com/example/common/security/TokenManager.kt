package com.example.common.security

import androidx.lifecycle.LiveData

interface TokenManager {
    val tokenObservable: LiveData<String?>
    fun checkAuthenticationByToken(token: String?): UserAuthentication
    fun getToken(): String?
}