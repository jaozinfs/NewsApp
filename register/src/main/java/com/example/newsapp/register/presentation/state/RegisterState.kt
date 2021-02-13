package com.example.newsapp.register.presentation.state

sealed class RegisterState {
    object Loading : RegisterState()
    object RegisterSuccessfully:  RegisterState()
    object RegisterError : RegisterState()
}