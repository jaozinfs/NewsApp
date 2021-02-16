package com.example.newsapp.network.di

import com.example.newsapp.network.RetrofitClientFactory
import com.example.newsapp.network.RetrofitClientFactoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkModules = module {
    single<RetrofitClientFactory> { RetrofitClientFactoryImpl() }
}