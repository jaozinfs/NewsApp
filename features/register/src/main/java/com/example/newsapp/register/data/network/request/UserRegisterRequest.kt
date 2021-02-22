package com.example.newsapp.register.data.network.request

data class UserRegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val birthDate: String? = null
)