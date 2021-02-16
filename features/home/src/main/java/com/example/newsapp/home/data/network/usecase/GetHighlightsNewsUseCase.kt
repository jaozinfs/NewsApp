package com.example.newsapp.home.data.network.usecase

import com.example.common.base.BaseUseCase
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsRepository

class GetHighlightsNewsUseCase(
    private val newsRepository: NewsRepository
) : BaseUseCase<Nothing?, List<News>> {

    override suspend fun execute(params: Nothing?): List<News> = newsRepository.getHighlightsNews()
}