package com.example.newsapp.home.di

import com.example.newsapp.home.data.datasource.NewsDataSource
import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.data.mapper.NewsMapperImpl
import com.example.newsapp.home.data.network.NewsApi
import com.example.newsapp.home.domain.usecases.GetHighlightsNewsUseCase
import com.example.newsapp.home.domain.usecases.GetNewsUseCase
import com.example.newsapp.home.data.repository.NewsLocalRepositoryImpl
import com.example.newsapp.home.data.repository.NewsNetworkRepositoryImpl
import com.example.newsapp.home.domain.NewsLocalRepository
import com.example.newsapp.home.domain.NewsNetworkRepository
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.domain.usecases.GetFavoritesNewsUseCase
import com.example.newsapp.home.presentation.viewmodel.FavoritesNewsViewModel
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManager
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManagerImpl
import com.example.newsapp.network.RetrofitClientFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModules = module {
    factory { get<RetrofitClientFactory>().createAuthenticatedClient(NewsApi::class.java) }
    single<NewsMapper> { NewsMapperImpl() }
    factory { NewsDataSource(get(), get()) }
    single<NewsNetworkRepository> {
        NewsNetworkRepositoryImpl(get(), get(), get())
    }
    single<NewsLocalRepository> {
        NewsLocalRepositoryImpl(get(), get())
    }
    single<ReloadNewsManager> { ReloadNewsManagerImpl() }
    factory { FavoriteNewsUseCase(get()) }
    factory { GetFavoritesNewsUseCase(get()) }
    factory {
        GetHighlightsNewsUseCase(
            get()
        )
    }
    factory { GetNewsUseCase(get()) }
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }
    viewModel { FavoritesNewsViewModel(get(), get()) }
}