package com.example.newsapp.home.presentation.state

import com.example.newsapp.home.domain.News

sealed class HomeState {
    object Loading: HomeState()
    data class FetchedHighLightsNews(val news: List<News>) : HomeState()
    object NewsSavedFavorite: HomeState()
    object NewsRemovedFromFavorite: HomeState()
    object  ReloadNews : HomeState()
    object Error :  HomeState()
}