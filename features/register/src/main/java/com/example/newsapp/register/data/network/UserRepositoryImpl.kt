package com.example.newsapp.register.data.network

import com.example.newsapp.network.NetworkRequesterManager
import com.example.newsapp.register.data.UserApi
import com.example.newsapp.register.data.network.request.UserRegisterRequest
import com.example.newsapp.register.domain.repository.Token
import com.example.newsapp.register.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun register(
        email: String,
        password: String,
        name: String,
        birthDate: String
    ): Token = NetworkRequesterManager.request {
        userApi.register(UserRegisterRequest(name, email, password, birthDate))
    }.token
}