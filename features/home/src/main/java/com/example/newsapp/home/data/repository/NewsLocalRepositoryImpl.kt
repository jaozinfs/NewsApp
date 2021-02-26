package com.example.newsapp.home.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsLocalRepository
import com.example.newsapp.local.database.dao.NewsDao

class NewsLocalRepositoryImpl(
    private val newsMapper: NewsMapper,
    private val newsDao: NewsDao
) : NewsLocalRepository {

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