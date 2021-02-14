package com.example.newsapp.login.di

import com.example.common.KoinModulesManager
import com.example.common.di.FeaturesModulesManager
import com.example.newsapp.BuildConfig
import com.example.newsapp.login.data.UserApi
import com.example.newsapp.login.data.network.UserRepositoryImpl
import com.example.newsapp.login.domain.repository.UserRepository
import com.example.newsapp.login.domain.usecases.GetUserSavedTokenUseCase
import com.example.newsapp.login.domain.usecases.SaveUserTokenUseCase
import com.example.newsapp.login.domain.usecases.UserLoginUseCase
import com.example.newsapp.login.presentation.LoginViewModel
import com.example.newsapp.network.RetrofitClientProvider
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModules = module {
    single<FeaturesModulesManager> { KoinModulesManager(this@module) }
    factory { RetrofitClientProvider.provideRetrofitApiByInterface<UserApi>(BuildConfig.BASE_URL) }
    single<UserRepository> {
        UserRepositoryImpl(
            get()
        )
    }
    factory { UserLoginUseCase(get()) }
    factory { SaveUserTokenUseCase(get()) }
    factory { GetUserSavedTokenUseCase(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
}