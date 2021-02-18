package com.example.newsapp.di

import com.example.newsapp.local.data.UserTokenManager
import com.example.common.TokenManager
import com.example.newsapp.local.domain.GetUserSavedTokenUseCase
import com.example.newsapp.local.domain.RemoveSavedTokenUseCase
import com.example.newsapp.security.TokenStorage
import com.example.newsapp.security.TokenStorageImpl
import com.example.newsapp.userpreferences.UserPreferencesRepository
import com.example.newsapp.userpreferences.UserPreferencesRepositoryImpl
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModules = module {
    single { SplitInstallManagerFactory.create(androidApplication()) }
    single { TokenStorageImpl } bind TokenStorage::class
    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl() }
    single { GetUserSavedTokenUseCase(get()) }
    single { RemoveSavedTokenUseCase(get()) }
    single<TokenManager> { UserTokenManager }
}
