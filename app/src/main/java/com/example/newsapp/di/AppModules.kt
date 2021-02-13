package com.example.newsapp.di

import com.example.newsapp.security.TokenStorage
import com.example.newsapp.security.TokenStorageImpl
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val commonModules = module {
    single { TokenStorageImpl } bind TokenStorage::class
}