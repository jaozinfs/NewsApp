package com.example.newsapp.home.data.repository

import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.data.network.NewsApi
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsRepository
import com.example.newsapp.network.NetworkRequesterManager

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsMapper: NewsMapper
) : NewsRepository {

    override suspend fun getHighlightsNews(): List<News> = NetworkRequesterManager.request {
        newsApi.getHighlightsNews()
    }.let { list ->
        newsMapper.mapToDomain(list)
    }

    override suspend  fun getNews(): List<News> {
        TODO("NOT IMPLEMENTS")
    }

}