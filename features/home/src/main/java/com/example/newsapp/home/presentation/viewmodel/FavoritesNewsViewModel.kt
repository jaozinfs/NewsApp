package com.example.newsapp.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.domain.usecases.GetFavoritesNewsUseCase
import com.example.newsapp.home.presentation.event.FavoritesEvents
import com.example.newsapp.home.presentation.state.FavoritesStates
import kotlinx.coroutines.launch

class FavoritesNewsViewModel(
    getFavoritesNewsUseCase: GetFavoritesNewsUseCase,
    private val favoriteNewsUseCase: FavoriteNewsUseCase
) :
    BaseViewModel<FavoritesEvents, FavoritesStates>() {
    private val _state = MutableLiveData<FavoritesStates>()

    override val state: LiveData<FavoritesStates>
        get() = _state

    val favoritesNews = getFavoritesNewsUseCase.execute(null).map {
        it.sortedByDescending { it.publishedAt }
    }

    private fun favoriteNews(news: News) {
        viewModelScope.launch {
            favoriteNewsUseCase.execute(news)
        }
    }

    override fun handleEvent(event: FavoritesEvents) {
        when (event) {
            is FavoritesEvents.FavoriteNews -> favoriteNews(event.news)
        }
    }
}