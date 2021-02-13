package com.example.newsapp.network

import com.example.newsapp.network.utils.GJsonExtension.addLogInterceptor
import com.example.newsapp.network.utils.GjsonUtils.gsonDefault
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientProvider {

    inline fun <reified T> provideRetrofitApiByInterface(baseUrl: String): T {
        val client = OkHttpClient.Builder().run {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG)
                addLogInterceptor()
            build()
        }

        return Retrofit.Builder().run {
            client(client)
            addConverterFactory(GsonConverterFactory.create(gsonDefault))
            baseUrl(baseUrl)
            build()
        }.create(T::class.java)
    }
}