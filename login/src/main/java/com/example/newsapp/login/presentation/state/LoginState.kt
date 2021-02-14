package com.example.newsapp.login.presentation.state

sealed class LoginState {
    object LoginSuccessfully : LoginState()
    object LoginError: LoginState()
    object Loading: LoginState()
}