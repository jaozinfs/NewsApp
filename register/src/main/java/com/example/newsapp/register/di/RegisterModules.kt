package com.example.newsapp.register.di

import com.example.newsapp.BuildConfig
import com.example.newsapp.network.RetrofitClientProvider
import com.example.newsapp.register.data.UserApi
import com.example.newsapp.register.data.network.UserRepositoryImpl
import com.example.newsapp.register.domain.repository.UserRepository
import com.example.newsapp.register.domain.usecases.GetUserSavedTokenUseCase
import com.example.newsapp.register.domain.usecases.SaveRegisteredTokenUserCase
import com.example.newsapp.register.domain.usecases.UserRegisterUseCase
import com.example.newsapp.register.presentation.viewmodel.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerModules = module {
    factory { RetrofitClientProvider.provideRetrofitApiByInterface<UserApi>(BuildConfig.BASE_URL) }
    single<UserRepository> {
        UserRepositoryImpl(
            get()
        )
    }
    factory { UserRegisterUseCase(get()) }
    factory { SaveRegisteredTokenUserCase(get()) }
    factory { GetUserSavedTokenUseCase(get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
}