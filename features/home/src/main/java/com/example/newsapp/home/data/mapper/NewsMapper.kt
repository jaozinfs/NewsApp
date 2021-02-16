package com.example.newsapp.home.data.mapper

import com.example.newsapp.home.data.network.NewsEntity
import com.example.newsapp.home.domain.News

interface NewsMapper {
    fun mapToDomain(list: List<NewsEntity>): List<News>
}