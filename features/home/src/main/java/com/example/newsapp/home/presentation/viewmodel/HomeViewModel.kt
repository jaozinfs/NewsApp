package com.example.newsapp.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.common.base.BaseViewModel
import com.example.common.base.execute
import com.example.newsapp.home.data.datasource.NewsDataSource
import com.example.newsapp.home.data.network.usecase.GetHighlightsNewsUseCase
import com.example.newsapp.home.presentation.event.HomeEvents
import com.example.newsapp.home.presentation.state.HomeState
import com.example.newsapp.local.domain.RemoveSavedTokenUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val removeSavedTokenUseCase: RemoveSavedTokenUseCase,
    private val newsDataSource: NewsDataSource
) : BaseViewModel<HomeEvents, HomeState>() {
    private val TAG = this@HomeViewModel::class.simpleName
    private val _state = MutableLiveData<HomeState>()

    override val removeTokenUseCase: (suspend () -> Unit)?
        get() = removeSavedTokenUseCase::execute

    override val state: LiveData<HomeState>
        get() = _state

    val newsListFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        newsDataSource
    }.flow
        .cachedIn(viewModelScope)

    override fun handleEvent(event: HomeEvents) {

    }
}