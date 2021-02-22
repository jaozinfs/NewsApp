package com.example.newsapp.register.domain.repository

typealias Token = String

interface UserRepository {

    suspend fun register(email: String, password: String, name: String, birthDate: String): Token

}