package com.example.newsapp.home.domain.usecases

import com.example.common.base.BaseUseCase
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsNetworkRepository

class GetHighlightsNewsUseCase(
    private val newsRepository: NewsNetworkRepository
) : BaseUseCase<Nothing?, List<News>> {

    override suspend fun execute(params: Nothing?): List<News> =
        newsRepository.getHighlightsNews().sortedBy {
            it.publishedAt
        }
}