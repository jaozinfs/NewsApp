package com.example.newsapp.home.data.mapper

import com.example.common.base.DataPaginated
import com.example.newsapp.home.data.network.NewsEntity
import com.example.newsapp.home.domain.News
import com.example.newsapp.network.base.BaseResponse
import  com.example.newsapp.local.database.entity.NewsEntity as NewsEntityDatabase

interface NewsMapper {
    fun mapNetworkToDomain(list: List<NewsEntity>): List<News>
    fun mapToEntity(news: News): NewsEntityDatabase
    fun mapLocalToDomain(news: List<NewsEntityDatabase>):  List<News>
    fun mapLocalToDomain(news: NewsEntityDatabase?):  News?
    fun mapPaginatedToDomain(baseResponse: BaseResponse<List<NewsEntity>>): DataPaginated<News>
}