package com.example.common.security

import androidx.lifecycle.LiveData

interface TokenManager {
    fun getToken(): LiveData<String?>
    fun checkAuthenticationByToken(token: String?): UserAuthentication
}