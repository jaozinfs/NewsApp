package com.example.newsapp.login.data.network

import com.example.newsapp.login.data.UserApi
import com.example.newsapp.login.data.network.request.UserLoginRequest
import com.example.newsapp.login.domain.repository.Token
import com.example.newsapp.login.domain.repository.UserRepository
import com.example.newsapp.network.NetworkRequesterManager

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Token = NetworkRequesterManager.request {
        userApi.login(UserLoginRequest(email, password))
    }.token
}