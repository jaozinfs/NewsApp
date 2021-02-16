package com.example.newsapp.network

import com.example.newsapp.network.interceptor.AuthenticateInterceptor
import com.example.newsapp.network.utils.GJsonExtension.addLogInterceptor
import com.example.newsapp.network.utils.GjsonUtils.gsonDefault
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientProvider : KoinComponent {

    fun <T> provideRetrofitApiByInterface(api: Class<T>, baseUrl: String): T =
        Retrofit.Builder().run {
            client(getOkHttpClient())
            addConverterFactory(GsonConverterFactory.create(gsonDefault))
            baseUrl(baseUrl)
            build()
        }.create(api)


    fun <T> provideRetrofitApiAuthenticatedByInterface(api: Class<T>, baseUrl: String) : T =
        Retrofit.Builder().run {
            client(getOkHttpClientAuthenticated())
            addConverterFactory(GsonConverterFactory.create(gsonDefault))
            baseUrl(baseUrl)
            build()
        }.create(api)

    private fun getOkHttpClient() =
        getOkHttpClientBuilder().build()

    private fun getOkHttpClientAuthenticated() =
        getOkHttpClientBuilder().apply {
            addInterceptor(AuthenticateInterceptor())
        }.build()

    private fun getOkHttpClientBuilder() =
        OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addClientLogInterceptor(this)
        }

    private fun addClientLogInterceptor(builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG)
            builder.addLogInterceptor()
    }

}