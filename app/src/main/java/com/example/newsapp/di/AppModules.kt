package com.example.newsapp.di

import com.example.common.UserTokenManager
import com.example.common.domain.GetUserSavedTokenUseCase
import com.example.common.security.TokenManager
import com.example.newsapp.security.TokenStorage
import com.example.newsapp.security.TokenStorageImpl
import com.example.newsapp.userpreferences.UserPreferencesRepository
import com.example.newsapp.userpreferences.UserPreferencesRepositoryImpl
import com.example.newsapp.userpreferences.di.userPreferencesModule
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModules = module {
    single { TokenStorageImpl } bind TokenStorage::class
    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl() }
    single { GetUserSavedTokenUseCase(get()) }
    single<TokenManager> { UserTokenManager(get()) }
}