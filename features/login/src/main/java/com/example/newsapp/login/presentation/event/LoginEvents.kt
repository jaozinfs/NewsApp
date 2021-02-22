package com.example.newsapp.login.presentation.event

sealed class LoginEvents {
    class Login(val email: String, val password: String) : LoginEvents()
    object CheckAuthentication : LoginEvents()
}