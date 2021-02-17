package com.example.newsapp.home.di

import com.example.newsapp.home.data.datasource.NewsDataSource
import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.data.mapper.NewsMapperImpl
import com.example.newsapp.home.data.network.NewsApi
import com.example.newsapp.home.data.network.usecase.GetHighlightsNewsUseCase
import com.example.newsapp.home.data.repository.NewsRepositoryImpl
import com.example.newsapp.home.domain.NewsRepository
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import com.example.newsapp.network.RetrofitClientFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModules = module {
    factory { get<RetrofitClientFactory>().createAuthenticatedClient(NewsApi::class.java) }
    single<NewsMapper> { NewsMapperImpl() }
    single<NewsRepository> {
        NewsRepositoryImpl(get(), get())
    }
    factory { GetHighlightsNewsUseCase(get()) }
    factory { NewsDataSource(get()) }
    viewModel { HomeViewModel(get(), get()) }
}