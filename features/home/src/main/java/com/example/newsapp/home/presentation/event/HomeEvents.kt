package com.example.newsapp.home.presentation.event

import com.example.newsapp.home.domain.News

sealed class HomeEvents {
    class FavoriteNews(val news: News) : HomeEvents()
    object FetchHighLightNews : HomeEvents()
    object Logout : HomeEvents()
}
