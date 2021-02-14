package com.example.newsapp.userpreferences.di

import com.example.newsapp.userpreferences.DataStoreFactory
import com.example.newsapp.userpreferences.DataStoreFactoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userPreferencesModule = module {
    single<DataStoreFactory> { DataStoreFactoryImpl(androidApplication()) }
    single { (fileName: String) ->
        get<DataStoreFactory>().createDataStore(fileName)
    }
}