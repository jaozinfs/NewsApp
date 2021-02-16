package com.example.newsapp.home.data.mapper

import com.example.newsapp.home.data.network.NewsEntity
import com.example.newsapp.home.domain.News

class NewsMapperImpl : NewsMapper {
    override fun mapToDomain(list: List<NewsEntity>): List<News> = list.map {
        News(
            it.title,
            it.description,
            it.content,
            it.author,
            it.publishedAt,
            it.highlight,
            it.url,
            it.imageUrl
        )
    }
}