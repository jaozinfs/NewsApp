package com.example.newsapp.home.domain.usecases

import androidx.lifecycle.LiveData
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsRepository

class GetFavoritesNewsUseCase(
    private val newsRepository: NewsRepository
) {
    fun execute(params: Any?): LiveData<List<News>> = newsRepository.getFavoritesNews()
}