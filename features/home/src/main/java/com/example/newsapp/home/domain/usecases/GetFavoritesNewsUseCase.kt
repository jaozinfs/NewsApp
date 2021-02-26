package com.example.newsapp.home.domain.usecases

import androidx.lifecycle.LiveData
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsLocalRepository

class GetFavoritesNewsUseCase(
    private val newsRepository: NewsLocalRepository
) {
    fun execute(): LiveData<List<News>> = newsRepository.getFavoritesNews()
}