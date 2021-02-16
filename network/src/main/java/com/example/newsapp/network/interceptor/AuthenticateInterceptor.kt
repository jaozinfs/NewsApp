package com.example.newsapp.network.interceptor

import com.example.common.security.TokenManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthenticateInterceptor : Interceptor, KoinComponent {
    private val tokenManager: TokenManager by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${tokenManager.getToken()}")
            .build()
        return chain.proceed(newRequest)
    }
}