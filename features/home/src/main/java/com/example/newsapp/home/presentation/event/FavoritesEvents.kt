package com.example.newsapp.home.presentation.event

import com.example.newsapp.home.domain.News

sealed class FavoritesEvents {
    class FavoriteNews(val news: News) : FavoritesEvents()
}
