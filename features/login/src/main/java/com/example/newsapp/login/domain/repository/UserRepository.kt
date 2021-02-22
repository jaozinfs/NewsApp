package com.example.newsapp.login.domain.repository

typealias Token = String

interface UserRepository {

    suspend fun login(email: String, password: String): Token

}