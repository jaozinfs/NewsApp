package com.example.newsapp.register.data

import com.example.newsapp.register.data.network.request.UserRegisterRequest
import com.example.newsapp.register.data.network.response.UserRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/v1/client/auth/signup")
    suspend fun register(@Body userRegisterRequest: UserRegisterRequest): Response<UserRegisterResponse>

}