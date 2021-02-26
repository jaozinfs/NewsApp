package com.example.newsapp.home.presentation.viewmodel

import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.base.BaseViewModel
import com.example.common.base.execute
import com.example.newsapp.home.domain.usecases.GetHighlightsNewsUseCase
import com.example.newsapp.home.domain.usecases.GetNewsUseCase
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.presentation.event.HomeEvents
import com.example.newsapp.home.presentation.state.HomeState
import com.example.newsapp.local.domain.RemoveSavedTokenUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val removeSavedTokenUseCase: RemoveSavedTokenUseCase,
    private val getHighlightsNewsUseCase: GetHighlightsNewsUseCase,
    private val reloadNewsManager: ReloadNewsManager,
    private val favoriteNewsUseCase: FavoriteNewsUseCase,
    getNewsUseCase: GetNewsUseCase
) : BaseViewModel<HomeEvents, HomeState>() {
    private val TAG = this@HomeViewModel::class.simpleName

    override val removeTokenUseCase: (suspend () -> Unit)?
        get() = removeSavedTokenUseCase::execute

    private val stateChannel = Channel<HomeState>(UNLIMITED)
    val stateFlow = stateChannel.receiveAsFlow()

    override val state: LiveData<HomeState>
        get() = stateChannel.receiveAsFlow().asLiveData()

    val newsListFlow = getNewsUseCase.execute(null).catch { e ->
        if (e is WindowManager.BadTokenException)
            removeTokenUseCase?.invoke()
    }.cachedIn(viewModelScope)

    init {
        observeRefreshNews()
    }

    private fun reloadAllNews() {
        getHighlightNews()
        reloadNews()
    }


    private fun getHighlightNews() {
        launchAuthenticated {
            val list = getHighlightsNewsUseCase.execute()
            stateChannel.send(HomeState.FetchedHighLightsNews(list))
        }
    }

    private fun reloadNews() {
        viewModelScope.launch {
            stateChannel.send(HomeState.ReloadNews)
        }
    }

    private fun favoriteItem(news: News) {
        viewModelScope.launch {
            val state = favoriteNewsUseCase.execute(news)
            updateFavoriteState(state)
        }
    }

    private fun updateFavoriteState(state: FavoriteNewsUseCase.FavoriteState) {
        viewModelScope.launch {
            stateChannel.send(
                when (state) {
                    FavoriteNewsUseCase.FavoriteState.Saved -> HomeState.NewsSavedFavorite
                    FavoriteNewsUseCase.FavoriteState.Removed -> HomeState.NewsRemovedFromFavorite
                }
            )
        }
    }


    override fun handleEvent(event: HomeEvents) = when (event) {
        is HomeEvents.FetchHighLightNews -> {
            getHighlightNews()
        }
        is HomeEvents.FavoriteNews -> {
            favoriteItem(event.news)
        }
        HomeEvents.Logout -> logout()
    }


    private fun observeRefreshNews() = viewModelScope.launch {
        reloadNewsManager.reloadNewsFlow.collect {
            handleReloadType(it)
        }
    }

    private fun handleReloadType(type: ReloadTypes) = when (type) {
        is ReloadTypes.HighLightsNews -> getHighlightNews()
        is ReloadTypes.News -> reloadNews()
        is ReloadTypes.AllNews -> reloadAllNews()
    }

    private fun logout() {
        viewModelScope.launch {
            removeSavedTokenUseCase.execute()
        }
    }
}