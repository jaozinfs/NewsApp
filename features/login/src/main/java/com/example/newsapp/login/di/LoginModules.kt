package com.example.newsapp.login.di

import com.example.common.KoinModulesManager
import com.example.common.di.FeaturesModulesManager
import com.example.newsapp.login.data.UserApi
import com.example.newsapp.login.data.network.UserRepositoryImpl
import com.example.newsapp.login.domain.repository.UserRepository
import com.example.newsapp.login.domain.usecases.SaveUserTokenUseCase
import com.example.newsapp.login.domain.usecases.UserLoginUseCase
import com.example.newsapp.login.presentation.LoginViewModel
import com.example.newsapp.network.RetrofitClientFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModules = module {
    single<FeaturesModulesManager> { KoinModulesManager(this@module) }
    factory { get<RetrofitClientFactory>().createClient(UserApi::class.java) }
    single<UserRepository> {
        UserRepositoryImpl(
            get()
        )
    }
    factory { UserLoginUseCase(get()) }
    factory { SaveUserTokenUseCase(get()) }
    viewModel { LoginViewModel(get(), get(), get(), get()) }
}