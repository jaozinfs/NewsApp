package com.example.newsapp.home.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getHighlightsNews(): List<News>
    fun getNews(): Flow<PagingData<News>>
    suspend fun favoriteNews(news: News)
    suspend fun removeFavoriteNews(news: News)
    suspend fun getNewsByUrl(url: String): News?
    fun getFavoritesNews(): LiveData<List<News>>
}