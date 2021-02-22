package com.example.newsapp.register.di

import com.example.newsapp.network.RetrofitClientFactory
import com.example.newsapp.register.data.UserApi
import com.example.newsapp.register.data.network.UserRepositoryImpl
import com.example.newsapp.register.domain.repository.UserRepository
import com.example.newsapp.register.domain.usecases.SaveRegisteredTokenUserCase
import com.example.newsapp.register.domain.usecases.UserRegisterUseCase
import com.example.newsapp.register.presentation.viewmodel.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerModules = module {
    factory { get<RetrofitClientFactory>().createClient(UserApi::class.java) }
    single<UserRepository> {
        UserRepositoryImpl(
            get()
        )
    }
    factory { UserRegisterUseCase(get()) }
    factory { SaveRegisteredTokenUserCase(get()) }
    viewModel { RegisterViewModel(get(), get()) }
}