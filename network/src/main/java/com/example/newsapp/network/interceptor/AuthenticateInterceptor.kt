package com.example.newsapp.network.interceptor

import com.example.common.TokenManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class AuthenticateInterceptor : Interceptor, KoinComponent {
    private val tokenManager: TokenManager by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = createNewRequestBuilder(chain)
        addTokenOnRequestHeader(newRequest)
        return chain.proceed(newRequest.build())
    }

    private fun getToken() =
        tokenManager.getCurrentToken()

    private fun createNewRequestBuilder(chain: Interceptor.Chain) = chain.request().newBuilder()

    private fun addTokenOnRequestHeader(builder: Request.Builder) {
        val token = getToken().also {
            Timber.d("Bearer $it")
        }
        builder.addHeader("Authorization", "Bearer $token")
    }
}