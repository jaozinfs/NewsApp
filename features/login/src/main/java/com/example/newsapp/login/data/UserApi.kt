package com.example.newsapp.login.data

import com.example.newsapp.login.data.network.request.UserLoginRequest
import com.example.newsapp.login.data.network.response.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/v1/client/auth/signin")
    suspend fun login(@Body userRegisterRequest: UserLoginRequest): Response<UserLoginResponse>

}