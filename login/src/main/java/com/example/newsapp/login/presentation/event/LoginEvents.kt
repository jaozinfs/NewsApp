package com.example.newsapp.login.presentation.event

sealed class LoginEvents {
    data class Login(val email: String, val password: String) : LoginEvents()
}