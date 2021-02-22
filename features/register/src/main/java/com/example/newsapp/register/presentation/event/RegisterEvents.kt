package com.example.newsapp.register.presentation.event

import com.example.newsapp.security.UserTokenState

sealed class RegisterEvents {
    data class Register(
        val email: String,
        val password: String,
        val name: String,
        val birthDate: String
    ) : RegisterEvents()

}