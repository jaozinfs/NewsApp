package com.example.newsapp.home.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.home.data.datasource.NewsDataSource
import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.data.network.NewsApi
import com.example.newsapp.home.domain.News
import com.example.newsapp.local.database.dao.NewsDao
import com.example.newsapp.network.NetworkRequesterManager
import kotlinx.coroutines.flow.Flow
import java.util.*

class FakeNewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsMapper: NewsMapper,
    private val newsDataSource: NewsDataSource,
    private val newsDao: NewsDao
) : NewsRepository {
    private val PAGE_SIZE = 10
    var id = 1
    override suspend fun getHighlightsNews(): List<News> = NetworkRequesterManager.request {
        newsApi.getHighlightsNews()
    }.let { response ->
        generateNews()
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
    val list = mutableListOf<News>()

    private fun generateNews(quantity: Int = 4): List<News> {
        repeat(quantity) {
            list.add(News(
                "Teste 1",
                "Teste desc 1",
                "Teste content 1",
                "João Victor",
                Date(),
                false,
                "www.google.com$id",
                "https://i2.wp.com/www.institutoniemeyer.org/wp-content/uploads/2018/09/teste.png"
            ).also {
                id++
            })
        }
        return list
    }
}