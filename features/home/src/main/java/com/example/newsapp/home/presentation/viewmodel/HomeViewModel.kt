package com.example.newsapp.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.base.BaseViewModel
import com.example.common.base.execute
import com.example.newsapp.home.data.network.usecase.GetHighlightsNewsUseCase
import com.example.newsapp.home.data.network.usecase.GetNewsUseCase
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.domain.usecases.GetFavoritesNewsUseCase
import com.example.newsapp.home.presentation.event.HomeEvents
import com.example.newsapp.home.presentation.state.HomeState
import com.example.newsapp.local.domain.RemoveSavedTokenUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val removeSavedTokenUseCase: RemoveSavedTokenUseCase,
    private val getHighlightsNewsUseCase: GetHighlightsNewsUseCase,
    private val reloadNewsManager: ReloadNewsManager,
    private val favoriteNewsUseCase: FavoriteNewsUseCase,
    getFavoritesNewsUseCase: GetFavoritesNewsUseCase,
    getNewsUseCase: GetNewsUseCase
) : BaseViewModel<HomeEvents, HomeState>() {
    private val TAG = this@HomeViewModel::class.simpleName
    private val _state = MutableLiveData<HomeState>()
    private val reloadNewsJob: Job? = null

    override val removeTokenUseCase: (suspend () -> Unit)?
        get() = removeSavedTokenUseCase::execute

    override val state: LiveData<HomeState>
        get() = _state

    val newsListFlow = getNewsUseCase.execute(null).cachedIn(viewModelScope)
    val favoritesNews = getFavoritesNewsUseCase.execute(null)

    init {
        observeReloadNews()
    }

    private fun reloadAllNews() {
        getHighlightNews()
        reloadNews()
    }


    private fun getHighlightNews() {
        launchAuthenticated {
            _state.value = HomeState.Loading
            _state.value = try {
                HomeState.FetchedHighLightsNews(getHighlightsNewsUseCase.execute())
            } catch (error: Exception) {
                HomeState.Error
                throw error
            }
        }
    }

    private fun reloadNews() {
        _state.value = HomeState.ReloadNews
    }

    private fun favoriteItem(news: News) {
        viewModelScope.launch {
            val state = favoriteNewsUseCase.execute(news)
            updateFavoriteState(state)
        }
    }

    private fun updateFavoriteState(state: FavoriteNewsUseCase.FavoriteState) {
        _state.value = when(state) {
            FavoriteNewsUseCase.FavoriteState.Saved -> HomeState.NewsSavedFavorite
            FavoriteNewsUseCase.FavoriteState.Removed -> HomeState.NewsRemovedFromFavorite
        }
    }


    override fun handleEvent(event: HomeEvents) = when (event) {
        is HomeEvents.FetchHighLightNews -> {
            getHighlightNews()
        }
        is HomeEvents.FavoriteNews -> {
            favoriteItem(event.news)
        }
    }



    private fun observeReloadNews() = viewModelScope.launch {
        reloadNewsManager.reloadNewsFlow.collect {
            handleReloadType(it)
        }
    }

    private fun handleReloadType(type: ReloadTypes) = when (type) {
        is ReloadTypes.HighLightsNews -> getHighlightNews()
        is ReloadTypes.News -> reloadNews()
        is ReloadTypes.AllNews -> reloadAllNews()
    }
}