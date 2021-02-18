package com.example.newsapp.home.data.network.usecase

import androidx.paging.PagingData
import com.example.common.base.BaseUseCase
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(
    private val newsRepository: NewsRepository
) {
    fun execute(params: Nothing?): Flow<PagingData<News>> = newsRepository.getNews()
}