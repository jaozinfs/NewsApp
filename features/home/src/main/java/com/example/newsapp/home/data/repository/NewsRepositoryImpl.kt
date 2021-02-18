package com.example.newsapp.home.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsapp.home.data.datasource.NewsDataSource
import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.data.network.NewsApi
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsRepository
import com.example.newsapp.local.database.dao.NewsDao
import com.example.newsapp.network.NetworkRequesterManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsMapper: NewsMapper,
    private val newsDataSource: NewsDataSource,
    private val newsDao: NewsDao
) : NewsRepository {
    private val PAGE_SIZE = 10

    override suspend fun getHighlightsNews(): List<News> = NetworkRequesterManager.request {
        newsApi.getHighlightsNews()
    }.let { response ->
        newsMapper.mapNetworkToDomain(response.data)
    }

    override fun getNews(): Flow<PagingData<News>> = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        newsDataSource
    }.flow

    override suspend fun favoriteNews(news: News) {
        newsDao.addNews(newsMapper.mapToEntity(news))
    }

    override suspend fun removeFavoriteNews(news: News) {
        newsDao.removeNews(newsMapper.mapToEntity(news))
    }

    override suspend fun getNewsByUrl(url: String): News? =
        newsMapper.mapLocalToDomain(newsDao.getNewsByUrl(url))

    override fun getFavoritesNews(): LiveData<List<News>> = newsDao.getNews().map {
        newsMapper.mapLocalToDomain(it)
    }

}