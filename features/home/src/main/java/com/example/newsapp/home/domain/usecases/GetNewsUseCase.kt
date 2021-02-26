package com.example.newsapp.home.domain.usecases

import androidx.paging.PagingData
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsNetworkRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(
    private val newsRepository: NewsNetworkRepository
) {
    fun execute(params: Nothing?): Flow<PagingData<News>> = newsRepository.getNews()
}