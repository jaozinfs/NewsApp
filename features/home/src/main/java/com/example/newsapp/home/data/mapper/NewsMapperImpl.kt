package com.example.newsapp.home.data.mapper

import com.example.common.base.DataPaginated
import com.example.common.base.Pagination
import com.example.newsapp.home.data.network.NewsEntity
import com.example.newsapp.home.domain.News
import com.example.newsapp.network.base.BaseResponse
import com.example.newsapp.local.database.entity.NewsEntity as NewsEntityDatabase

class NewsMapperImpl : NewsMapper {
    override fun mapNetworkToDomain(list: List<NewsEntity>): List<News> = list.map {
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

    override fun mapToEntity(news: News): NewsEntityDatabase =
        news.run {
            NewsEntityDatabase(
                title,
                description,
                content,
                author,
                publishedAt,
                highlight,
                url,
                imageUrl
            )
        }

    override fun mapLocalToDomain(news: List<NewsEntityDatabase>): List<News> =
        news.map {
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

    override fun mapLocalToDomain(news: NewsEntityDatabase?): News? =
        news?.let {
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

    override fun mapPaginatedToDomain(baseResponse: BaseResponse<List<NewsEntity>>): DataPaginated<News> =
        DataPaginated(
            Pagination(
                baseResponse.pagination?.currentPage,
                baseResponse.pagination?.totalPage
            ),
            mapNetworkToDomain(baseResponse.data)
        )

}