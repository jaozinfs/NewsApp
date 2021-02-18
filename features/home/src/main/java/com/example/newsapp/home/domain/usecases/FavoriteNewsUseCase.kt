package com.example.newsapp.home.domain.usecases

import com.example.common.base.BaseUseCase
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsRepository

class FavoriteNewsUseCase(
    private val newsRepository: NewsRepository
) : BaseUseCase<News, FavoriteNewsUseCase.FavoriteState> {

    override suspend fun execute(params: News): FavoriteState {
        val news = newsRepository.getNewsByUrl(params.url)
        return if (news == null) {
            newsRepository.favoriteNews(params)
            FavoriteState.Saved
        } else {
            newsRepository.removeFavoriteNews(params)
            FavoriteState.Removed
        }
    }

    sealed class FavoriteState {
        object Saved : FavoriteState()
        object Removed : FavoriteState()
    }
}