package com.example.newsapp.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.base.execute
import com.example.newsapp.home.data.network.usecase.GetHighlightsNewsUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val getHighlightsNewsUseCase: GetHighlightsNewsUseCase
) : ViewModel() {
    private val TAG = this@HomeViewModel::class.simpleName

    fun getHighLightNews() {
         viewModelScope.launch {
             val news = getHighlightsNewsUseCase.execute()
             Timber.tag(TAG).d(news.toString())
        }
    }

}