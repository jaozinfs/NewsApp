package com.example.newsapp.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.home.data.datasource.NewsDataSource
import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.data.network.NewsApi
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsNetworkRepository
import com.example.newsapp.network.NetworkRequesterManager
import kotlinx.coroutines.flow.Flow

class NewsNetworkRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsMapper: NewsMapper,
    private val newsDataSource: NewsDataSource
) : NewsNetworkRepository {
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

}